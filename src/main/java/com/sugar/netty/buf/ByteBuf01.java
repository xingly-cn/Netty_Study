package com.sugar.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 功能描述: Buf 01
 *
 * @author XiaoNianXin
 * @date 2021/12/28 13:53
 */
public class ByteBuf01 {
    public static void main(String[] args) {
        // Netty 的 Buf 无需 flip 反转
        // 读写有两个指针，写指针++，读指针还在0，访问时从0开始，无需反转
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i * 2);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
    }
}
