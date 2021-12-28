package com.sugar.netty.XChatGroup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * 功能描述: Netty 群聊 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/28 14:15
 */
public class GroupServer {
    private final int port;

    public GroupServer (int port) {
        this.port = port;
    }

    // 处理客户端请求
    public void run () throws InterruptedException {
        // 创建线程组
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);

        try {
            // 启动器
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 添加处理器
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("我的解码器",new StringDecoder());
                            pipeline.addLast("我的编码器",new StringEncoder());
                            pipeline.addLast("我的业务处理器",new GroupServerHandler());
                        }
                    });

            System.out.println("Netty 启动完成");
            ChannelFuture cf = bootstrap.bind(port).sync();
            cf.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        GroupServer groupServer = new GroupServer(7000);
        groupServer.run();
    }
}
