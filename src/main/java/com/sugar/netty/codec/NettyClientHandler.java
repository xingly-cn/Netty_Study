package com.sugar.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * 功能描述: Protobuf传输 - 客户端处理器
 *
 * @author XiaoNianXin
 * @date 2021/12/25 15:18
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪时
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 向服务端发送数据
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("Netty").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    /**
     * 读取服务端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }
}
