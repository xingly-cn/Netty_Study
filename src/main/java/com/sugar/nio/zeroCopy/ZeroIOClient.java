package com.sugar.nio.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 功能描述: 零拷贝IO传输大文件 - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 13:16
 */
public class ZeroIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",6666));

        String fileName = "d:\\protoc-3.6.1-win32.zip";
        FileChannel channel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        // linux：一次 transferTo 即可
        // windows：一次只能传输 8M，需要分段传输
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);

        System.out.println("发送字节：" + transferCount + " 耗时：" + (System.currentTimeMillis() - startTime));
        channel.close();

    }
}
