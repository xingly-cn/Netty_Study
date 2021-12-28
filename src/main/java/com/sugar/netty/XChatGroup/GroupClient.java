package com.sugar.netty.XChatGroup;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 功能描述: Netty 群聊 - 客户端
 *
 * @author XiaoNianXin
 * @date 2021/12/28 14:47
 */
public class GroupClient {
    private final String host;
    private final int port;

    public GroupClient (String host,int port) {
        this.host = host;
        this.port = port;
    }

    public void run () throws InterruptedException {
        // 创建线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 添加处理器
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            pipeline.addLast("我的解码器",new StringDecoder());
                            pipeline.addLast("我的编码器",new StringEncoder());
                            pipeline.addLast("我的业务处理器",new GroupClientHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            cf.channel().closeFuture();

            //----------------------------------------------------------------------------------
            Channel channel = cf.channel();
            System.out.println("[客户端 - " + channel.localAddress() + "] 启动完毕");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 发送数据
                channel.writeAndFlush(msg + "\n") ;
            }
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GroupClient groupClient = new GroupClient("127.0.0.1", 7000);
        groupClient.run();
    }
}
