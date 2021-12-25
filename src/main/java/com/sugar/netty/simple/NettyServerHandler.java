package com.sugar.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * 功能描述: Netty入门案例 - 服务端处理器
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
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端1",StandardCharsets.UTF_8));
    }

    /**
     * 读取客户端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 如果这里有一个耗时的业务，不可能在这里等待，异步执行 ---> 任务队列

        // 解决方案1：用户自定义普通任务
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10 * 1000);
//                }catch (Exception e) {
//                    System.out.println("自定义普通任务 执行异常");
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端2",StandardCharsets.UTF_8));
//            }
//        });

        // 解决方案2：用户自定义定时任务
//        ctx.channel().eventLoop().schedule(new Runnable() {
//            @Override
//            public void run() {
//                ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端2",StandardCharsets.UTF_8));
//            }
//        },5, TimeUnit.SECONDS);

        // 解决方案3：非 Reactor 调用 Channel 方法
        // 略


//        普通业务
//        System.out.println("当前线程 = " + Thread.currentThread().getName());
//        System.out.println("当前频道 = " + ctx.channel());      //
//        System.out.println("当前管道 = " + ctx.pipeline());     // 底层双向链表
//
//        // 接受客户端消息
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送：" + buf.toString(StandardCharsets.UTF_8));
//        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }


}
