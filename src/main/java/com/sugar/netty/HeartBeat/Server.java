package com.sugar.netty.HeartBeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 心跳检测 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/28 18:14
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))  // boss 添加日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 处理空闲状态的处理器 - 触发后交由责任链下一位处理
                            // readerTime - 多久没读
                            // writerTime - 多久没写
                            // allTime - 多久没读写
                            pipeline.addLast("空闲状态处理器",new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 空闲处理器
                            pipeline.addLast("我的空闲处理器",new ServerHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.bind(7000).sync();
            cf.channel().closeFuture().sync();
            System.out.println("服务器启动完毕");
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
