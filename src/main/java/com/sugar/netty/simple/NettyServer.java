package com.sugar.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 功能描述: Netty入门案例 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 14:48
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建 bossgroup 和 workergroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 4 个循环事件组 [1,2,3,4] 顺序分配，当 第五个客户端连接时重头分配 1
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try {
            // 服务器启动对象 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)  // 设置两个线程组
                    .channel(NioServerSocketChannel.class)  // 使用 NioServerSocketChannel
                    .option(ChannelOption.SO_BACKLOG,128)   // 设置线程队列 连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)  // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 给管道设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器准备完成！");

            // 绑定端口 - 同步处理
            // 启动服务器
            ChannelFuture cf = bootstrap.bind(6666).sync();

            // 关闭通道监听
            cf.channel().closeFuture().sync();
        }finally {
            // 出现异常直接关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
