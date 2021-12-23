package com.sugar.nio.project1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述: X聊天室 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/23 20:44
 */
public class XChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    public XChatServer() throws IOException {
        selector = Selector.open();
        listenChannel = ServerSocketChannel.open();
        listenChannel.configureBlocking(false);
        listenChannel.socket().bind(new InetSocketAddress(PORT));
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        while (true) {
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel client_channel = listenChannel.accept();
                        client_channel.configureBlocking(false);
                        client_channel.register(selector,SelectionKey.OP_READ);
                        System.out.println(client_channel.getRemoteAddress() + " 上线！");
                    }
                    if (key.isReadable()) {
                        readData(key);
                    }
                    iterator.remove();
                }

            }else {
                System.out.println("等待中......");
            }

        }
    }

    // 从客户端读取数据
    public void readData(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = channel.read(buffer);
        if (length > 0) {
            // 服务端接受客户端消息
            String msg = new String(buffer.array());
            System.out.println("From 客户端：" + msg);
            // 消息群发给其他客户端 - 排除自己
            sendMsgToAll(msg,channel);
        }else {
            // 服务端接收不到消息
            System.out.println(channel.getRemoteAddress() + " 离线！");
            // 取消注册,关闭通道
            key.cancel();
            channel.close();
        }
    }

    // 转发消息
    public void sendMsgToAll(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中......");
        // 遍历所有key,并获取频道,将自己排除掉
        for (SelectionKey key : selector.keys()) {
            Channel target_channel = key.channel();
            if (target_channel instanceof SocketChannel && target_channel != self) {
                // 转发
                SocketChannel channel = (SocketChannel)target_channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                channel.write(buffer);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        // 启动服务端
        XChatServer xChatServer = new XChatServer();
        xChatServer.listen();
    }
}
