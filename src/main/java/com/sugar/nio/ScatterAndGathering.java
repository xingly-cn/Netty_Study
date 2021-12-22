package com.sugar.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 功能描述: Scatter：将数据写入 buffer 时，采用 buffer 数组，依次写入 [分散]
 *        Gathering：从 buffer 读取时，采用 buffer 数组，依次读入[聚集]
 *
 * @author XiaoNianXin
 * @date 2021/12/22 14:44
 */
public class ScatterAndGathering {
    public static void main(String[] args) throws IOException {

        // 创建 socket 频道
        ServerSocketChannel channel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到 socket，并启动
        channel.socket().bind(inetSocketAddress);

        // buffer - 服务端
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 监听客户端连接
        SocketChannel socketChannel = channel.accept();
        while (true) {
            int byteRead = 0;
            while (byteRead < 8) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("当前读取" + byteRead + "个字符");
                Arrays.asList(byteBuffers).stream().map(
                        byteBuffer -> "postion:" +
                        byteBuffer.position() + ", limit:" +
                        byteBuffer.limit()
                ).forEach(System.out::println);
            }

            // 反转
            Arrays.asList(byteBuffers).forEach(
                    byteBuffer -> byteBuffer.flip()
            );

            // 数据回显客户端
            long byteWrite = 0;
            while (byteWrite < 8) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            // 复位
            Arrays.asList(byteBuffers).forEach(
                    byteBuffer -> byteBuffer.clear()
            );
        }

    }
}
