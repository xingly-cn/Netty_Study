package com.sugar.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 功能描述: HTTP 服务端初始化器
 *
 * @author XiaoNianXin
 * @date 2021/12/26 17:01
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入解码器
        pipeline.addLast("我的解码器",new HttpServerCodec());

        // 加入自定义处理器
        pipeline.addLast("我的处理器",new HttpServerHandler());
    }
}
