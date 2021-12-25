package com.sugar.nio.zeroCopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 功能描述: 普通IO传输大文件 - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 13:05
 */
public class IOCilent {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",7001);

        String fileName = "d:\\protoc-3.6.1-win32.zip";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount = 0;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            outputStream.write(buffer);
        }

        System.out.println("发送字节数：" + total + " 耗时：" + (System.currentTimeMillis() - startTime));
        outputStream.close();
        socket.close();
        inputStream.close();

    }
}
