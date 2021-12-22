package com.sugar.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述: 本地文件读数据
 *
 * @author XiaoNianXin
 * @date 2021/12/22 11:41
 */
public class NIOFileChannel_02 {
    public static void main(String[] args) throws IOException {

        // 创建输入流
        FileInputStream inputStream = new FileInputStream(new File("d:\\file.txt"));

        // 获取 channel
        FileChannel channel = inputStream.getChannel();

        // 创建 Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 输入流写入 Buffer
        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
        inputStream.close();
    }
}
