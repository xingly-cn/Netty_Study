package com.sugar.nio;

import java.nio.ByteBuffer;

/**
 * 功能描述: 只读 Buffer
 *
 * @author XiaoNianXin
 * @date 2021/12/22 14:25
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();

        // 只读 buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        // 读取
        while (readOnlyBuffer.hasRemaining()) {
            byte b = readOnlyBuffer.get();
            System.out.println("b = " + b);
        }
    }
}
