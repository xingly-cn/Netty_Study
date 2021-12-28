package com.sugar.netty.WebSocket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * 功能描述: 长连接 - 服务端
 *
 * @author XiaoNianXin
 * @date 2021/12/28 18:48
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

                            // 基于 http 协议,加入 http 解码器编码器
                            pipeline.addLast(new HttpServerCodec());

                            // 以块方式写,加入 块处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            // http 数据传输中是分段的,加入 段聚合处理器
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            // websocket 数据以帧传输
                            // 浏览器请求URL: ws://127.0.0.1:7000/hello
                            // 核心功能将 http协议 ------> ws协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            // 我的业务处理器
                            pipeline.addLast(new ServerHandler());
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
