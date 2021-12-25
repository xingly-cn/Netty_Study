package com.sugar.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * 功能描述: Netty入门案例 - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 14:48
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 创建事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            // 客户端启动对象 配置参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备完成！");

            // 启动客户端 连接服务器
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6666).sync();

            // 关闭通道监听
            cf.channel().close().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }

    }
}
