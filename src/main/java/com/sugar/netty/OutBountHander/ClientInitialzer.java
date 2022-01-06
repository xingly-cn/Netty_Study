package com.sugar.netty.OutBountHander;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端初始化器
 */
public class ClientInitialzer extends ChannelInitializer<SocketChannel> {
    /**
     * 向管道引入处理器
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 出站编码器
        pipeline.addLast(new LongToByteEncoder());
        // 业务处理器
        pipeline.addLast(new ClientHandler());
    }
}