package com.sugar.nio;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * 功能描述: 文件拷贝
 *
 * @author XiaoNianXin
 * @date 2021/12/22 13:49
 */
public class NIOFileChannel_04 {
    public static void main(String[] args) throws IOException {

        // 创建流
        FileInputStream fileInputStream = new FileInputStream(new File("d:\\a.jpg"));
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\b.jpg");

        // 获取 Channel
        FileChannel channel_in = fileInputStream.getChannel();
        FileChannel channel_out = fileOutputStream.getChannel();

        // 完成拷贝
        channel_out.transferFrom(channel_in,0,channel_in.size());

        // 关闭流
        channel_in.close();
        channel_out.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
