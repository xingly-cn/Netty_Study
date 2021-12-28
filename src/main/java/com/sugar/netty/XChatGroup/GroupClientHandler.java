package com.sugar.netty.XChatGroup;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 功能描述: 客户端处理器
 *
 * @author XiaoNianXin
 * @date 2021/12/28 15:29
 */
public class GroupClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}
