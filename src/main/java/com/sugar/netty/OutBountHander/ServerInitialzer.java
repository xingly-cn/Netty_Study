package com.sugar.netty.OutBountHander;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 服务端初始化器
 */
public class ServerInitialzer extends ChannelInitializer<SocketChannel> {
    /**
     * 向管道引入处理器
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 入站解码器
        pipeline.addLast(new ByteToLongDecoder());
        // 出站解码器
        pipeline.addLast(new LongToByteEncoder());
        // 业务处理器
        pipeline.addLast(new ServerHandler());

    }
}
