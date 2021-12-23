package com.sugar.nio.project1;

import com.sun.scenario.effect.impl.prism.PrImage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 功能描述: X聊天室 - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/23 21:09
 */
public class XChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String UserName;

    public XChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        UserName = socketChannel.getLocalAddress().toString();
        System.out.println(UserName + " 成功连接服务器...");
    }

    public void sendMsg(String msg) throws IOException {
        msg = UserName + "：" + msg;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
    }

    // 客户端也需监听服务端发来的消息 - 使用 select
    public void readMsg() throws IOException {
        int read = selector.select();
        if (read > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    String msg = new String(buffer.array());
                    System.out.println(msg.trim());
                }
            }
        }else {
            System.out.println("没收到消息......");
        }
    }


    public static void main(String[] args) throws IOException {
        // 启动客户端
        XChatClient xChatClient = new XChatClient();

        // 启动线程 - 每隔 3s 读取服务器发送的数据
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        xChatClient.readMsg();
                        Thread.currentThread().sleep(3000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 向服务器发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            xChatClient.sendMsg(msg);
        }
    }
}
