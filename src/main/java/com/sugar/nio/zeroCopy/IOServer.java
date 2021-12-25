package com.sugar.nio.zeroCopy;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 功能描述: 普通IO传输大文件 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 13:05
 */
public class IOServer {
    public static void main(String[] args) throws IOException {

        ServerSocket socket = new ServerSocket(7001);
        while (true) {
            Socket accept = socket.accept();
            DataInputStream inputStream = new DataInputStream(socket.accept().getInputStream());
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int read = inputStream.read(bytes, 0, bytes.length);
                    if (read == -1) {
                        break;
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
