package com.sugar.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;


/**
 * 功能描述: Protobuf传输 - 服务端处理器
 *
 * @author XiaoNianXin
 * @date 2021/12/25 15:01
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
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
     * 读取完成后
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 回复客户端消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("接受完毕",StandardCharsets.UTF_8));
    }

    /**
     * 读取客户端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取客户端发送的 Protobuf 数据
        StudentPOJO.Student stu = (StudentPOJO.Student) msg;
        System.out.println(stu.getId() + " - " +stu.getName());
    }


}
