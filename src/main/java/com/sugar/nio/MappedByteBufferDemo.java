package com.sugar.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述: 文件修改 - 内存 Buffer - 无需拷贝
 *
 * @author XiaoNianXin
 * @date 2021/12/22 14:32
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\file.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1：读写模式
         * 参数2：修改起始位
         * 参数3：文件映射到内存的大小
         * 直接修改范围就是 [0,5)
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte) 'H');
        map.put(3,(byte) '9');

        randomAccessFile.close();

    }
}
