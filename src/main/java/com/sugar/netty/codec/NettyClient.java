package com.sugar.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;


/**
 * 功能描述: Protobuf传输 - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/25 14:48
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 创建事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            // 客户端启动对象 配置参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 加入 Protobuf 编码器
                            socketChannel.pipeline().addLast(new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备完成！");

            // 启动客户端 连接服务器
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6666).sync();

            // 关闭通道监听
            cf.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }

    }
}
