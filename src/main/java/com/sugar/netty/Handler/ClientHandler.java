package com.sugar.netty.Handler;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 客户端入站
 */
public class ClientHandler extends SimpleChannelInboundHandler<Long> {
    /**
     * 接受服务端消息
     * @param ctx
     * @param aLong
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long aLong) throws Exception {
        System.out.println("【服务器：" + ctx.channel().remoteAddress() + "】 回复 |" + aLong);

    }

    /**
     * 有客户端连接时
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientHandler 发送数据");
//        ctx.writeAndFlush(123456L);
        // 如果这里发送的数据类型 和 定义的不一样，则不会触发 编码器
        ctx.writeAndFlush(123456L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
