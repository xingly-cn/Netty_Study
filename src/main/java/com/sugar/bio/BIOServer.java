package com.sugar.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能描述: BIO 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/16 20:59
 */

public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 1、创建线程池
        ExecutorService service = Executors.newCachedThreadPool();

        // 2、创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动完毕！");

        // 3、监听,等待客户端连接
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            // 创建线程
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // 客户端通讯方法
    public static void handler(Socket socket) throws IOException {
        // 线程信息
        System.out.println("当前通讯线程ID：" + Thread.currentThread().getId() + " 名称：" + Thread.currentThread().getName());
        // socket 获取输入流
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        // 循环读取客户端发送的数据
        while (true) {
            // 线程信息
            System.out.println("当前通讯线程ID：" + Thread.currentThread().getId() + " 名称：" + Thread.currentThread().getName());
            int read = inputStream.read(bytes);
            if (read != -1) {
                System.out.println("客户端发送数据：" + new String(bytes));
            } else {
                break;
            }
        }
        // 关闭 socket
        System.out.println("关闭客户端连接");
        socket.close();
    }
}
