package com.sugar.netty.OutBountHander;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Long> {
    /**
     * 有客户端发送数据时
     * @param ctx
     * @param aLong
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long aLong) throws Exception {

    }

    /**
     * 有客户端连接时
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientHandler 发送数据");
        ctx.writeAndFlush(123456L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
