# 一、BIO 模型

## 1.1 介绍

传统的 IO 编程。`同步阻塞`一个连接对应一个线程，只要有客户端请求就得启动一个线程，尽管这个连接不操作，线程也依旧存在，造成资源浪费，可通过线程池改善，适用于连接数较少且固定的架构。

## 1.2 工作机制

1、服务器启动一个 ServerSocket。

2、客户端启动 Socket 对服务器进行通信，默认服务器与每个客户建立一个线程。

3、客户端发出请求，询问服务器有无线程响应，没有则等待或被拒绝。

4、有响应，客户端线程等待请求结束后，再继续执行。



## 1.3 BIO 实例

任务说明：

1）使用 BIO 模型编写服务端，监控 6666 端口，客户端连接时启动一个线程与之通讯。

2）要求使用线程池机制，连接多个客户端。

3）服务器接受客户端发送的数据（telent方式）

```java
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
        byte[] bytes = new byte[1024];
        // socket 获取输入流
        InputStream inputStream = socket.getInputStream();
        // 循环读取客户端发送的数据
        while (true) {
            int read = inputStream.read(bytes);
            if (read != -1) {
                System.out.println("客户端发送数据：" + new String(bytes));
            }else {
                break ;
            }
        }
        // 关闭 socket
        System.out.println("关闭客户端连接");
        socket.close();
    }
}
```

## 1.4 BIO 问题分析

1、每个请求都需创建新线程。

2、并发数较大时，需要创建大量线程，耗费资源。

3、建立连接后，当线程无数据可读时，`阻塞在 Read`操作上，浪费资源。



# 二、NIO 模型

## 2.1 介绍

NIO 包括三大核心 [Channel,Buffer,Selector]。面向`缓冲区、块`编程，数据读入缓存区，需要时在缓冲区中移动，增加处理灵活性，是`同步非阻塞`的高伸缩性网络。

## 2.2 工作机制

1、一个线程从通道发送请求或读取数据，仅能获得当前可用的数据。无数据可用时不读取，`但不会线程阻塞，在可用数据没来之前，该线程仍可做其他事情`。



2、一个线程可以处理多个客户端。假设有 1000 个请求，分配 100 线程，每个线程处理 10 个请求。不会像 BIO 分配 1000 个线程。



3、HTTP2.0 采用多路复用技术，同一个连接并发处理多个请求，并发数量比 HTTP1.1 高好几个数量级。
