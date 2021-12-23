package com.sugar.nio.project;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述: NIO - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/23 19:46
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 创建频道
        SocketChannel channel = SocketChannel.open();

        // 设置非阻塞
        channel.configureBlocking(false);

        // 连接失败 - 非阻塞
        if (!channel.connect(new InetSocketAddress("127.0.0.1",6666))) {
            while (!channel.finishConnect()) {
                System.out.println("连接需要时间，客户端不阻塞");
            }
        }

        // 连接成功
        String str = "Hello Netty";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据
        channel.write(buffer);
        System.in.read();
    }
}
