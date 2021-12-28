package com.sugar.netty.XChatGroup;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 功能描述: 服务端处理器
 *
 * @author XiaoNianXin
 * @date 2021/12/28 14:56
 */
public class GroupServerHandler extends SimpleChannelInboundHandler<String> {
    // 定义所有 Channel 组，方便管理
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 连接建立后,第一执行 - 客户端可见
     * 将当前 Channel 加入组中,并通知其他用户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 推送给其他客户端,当前用户上线了
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端 - " + channel.remoteAddress() + "] 加入聊天!\n");
        System.out.println("当前在线用户：" + channelGroup.size());

        // 加入频道组
        channelGroup.add(channel);
    }

    /**
     * 当前 Channel 断开,通知其他用户 - 客户端可见
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端 - " + channel.remoteAddress() + "] 离开聊天!\n");
        System.out.println("当前在线用户：" + channelGroup.size());
    }

    /**
     * Channel 处于活动状态 - 服务端可见
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端 - " + ctx.channel().remoteAddress() + "] 上线！\n");
    }

    /**
     * Channel 处于非活动状态 - 服务端可见
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端 - " + ctx.channel().remoteAddress() + "] 离线！\n");
    }

    /**
     * 接受客户端数据,并转发
     * @param ctx
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        // 发送者频道
        Channel channel = ctx.channel();

        // 转发,除去自己
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("[客户端 - " + channel.remoteAddress() + "] 说：" + s + "\n");
            }
        });
    }

    /**
     * 异常关闭
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
