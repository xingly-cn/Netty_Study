package com.sugar.nio;

import java.nio.ByteBuffer;

/**
 * 功能描述: Buffer put 和 get 需同类型
 *
 * @author XiaoNianXin
 * @date 2021/12/22 14:19
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(64);

        // 存入
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('云');
        buffer.putShort((short)4);

        // 取出
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }
}
