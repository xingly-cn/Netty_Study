package com.sugar.nio;

import java.nio.IntBuffer;

/**
 * 功能描述: Buffer 使用
 *
 * @author XiaoNianXin
 * @date 2021/12/22 10:27
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建 IntBuffer  - 存放 5 个 int 数据
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 填充数据
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i * 2);
        }

        // 读数据 - 切换读模式
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            int i = intBuffer.get();
            System.out.println("i = " + i);
        }
    }
}
