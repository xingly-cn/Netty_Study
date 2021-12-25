package com.sugar.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * 功能描述: Netty入门案例 - 服务端处理器
 *
 * @author XiaoNianXin
 * @date 2021/12/25 15:01
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 发送异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    /**
     * 读取完成后
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 回复客户端消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端",StandardCharsets.UTF_8));
    }

    /**
     * 读取数据事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ctx = " + ctx);

        // 接受客户端消息
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送：" + buf.toString(StandardCharsets.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }


}
