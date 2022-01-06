package com.sugar.netty.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Long> {
    /**
     * 接受客户端消息
     * @param ctx
     * @param aLong
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long aLong) throws Exception {
        System.out.println("【客户：" + ctx.channel().remoteAddress() + "】 发送消息 |" + aLong);

        // 回送客户端数据
        ctx.writeAndFlush(987599L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
