package com.sugar.nio.zeroCopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 功能描述: 零拷贝IO传输大文件 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 13:16
 */
public class ZeroIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress("127.0.0.1",6666));

        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel channel = serverSocketChannel.accept();
            int read = channel.read(buffer);
            if(read == -1) {
                break;
            }
            buffer.rewind();
        }
    }
}
