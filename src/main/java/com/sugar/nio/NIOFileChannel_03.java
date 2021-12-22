package com.sugar.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述:
 *
 * @author XiaoNianXin
 * @date 2021/12/22 11:56
 */
public class NIOFileChannel_03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\file.txt"));
        FileChannel channel_in = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file2.txt");
        FileChannel channel_out = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 循环读取
        while (true) {
            // 重置标记位
            buffer.clear();

            int write = channel_in.read(buffer);
            if (write == -1) {
                break ;
            }
            buffer.flip();
            channel_out.write(buffer);
        }
        // 关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
