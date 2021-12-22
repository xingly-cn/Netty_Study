package com.sugar.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述: 本地文件写数据
 *
 * @author XiaoNianXin
 * @date 2021/12/22 11:25
 */
public class NIOFileChannel_01 {
    public static void main(String[] args) throws IOException {

        String data = "Hello Netty";

        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file.txt");

        // 获取 FileChannel
        FileChannel channel = fileOutputStream.getChannel();

        // 创建 Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 数据写入 Buffer
        byteBuffer.put(data.getBytes());

        // Buffer 写入 FileChannel
        byteBuffer.flip();
        channel.write(byteBuffer);

        // 关闭流
        fileOutputStream.close();
    }
}
