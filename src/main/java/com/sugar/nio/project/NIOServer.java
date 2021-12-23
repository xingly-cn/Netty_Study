package com.sugar.nio.project;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述: NIO 服务端 - 非阻塞
 *
 * @author XiaoNianXin
 * @date 2021/12/23 19:05
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建频道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到 Selector
        Selector selector = Selector.open();

        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        // serverSocketChannel 注册到 Selector | ops：连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 监听客户端
        while (true) {
            if (selector.select(1000) == 0) { // 1s 内无连接事件发生,跳过
                System.out.println("服务器等待 1 s，无连接");
                continue;
            }
            // 有连接事件,获取相关 Key,反向获取频道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {   // 有新客户端连接
                    // 给客户端生成 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 注册到 selector 并分配 buffer[服务端]
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    System.out.println("客户端连接成功，生成Channel：" + socketChannel.hashCode());
                }
                if (key.isReadable()) {   // 读事件
                    // key 获取频道
                    SocketChannel channel = (SocketChannel) key.channel();
                    // key 获取 buffer[服务端]
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    // 数据放入 buffer[服务端]
                    channel.read(buffer);
                    System.out.println("客户端发送数据：" + new String(buffer.array()));
                }
                // key 已被操作,移出 selectionKeys
                iterator.remove();
            }
        }

    }
}
