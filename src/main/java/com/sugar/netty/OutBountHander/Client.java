package com.sugar.netty.OutBountHander;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ClientInitialzer());
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 7000);
            cf.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }
}
