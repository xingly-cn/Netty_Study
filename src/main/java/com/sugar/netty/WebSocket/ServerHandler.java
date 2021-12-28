package com.sugar.netty.WebSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * 功能描述: 服务端处理器
 * TextWebSocketFrame 文本帧
 * @author XiaoNianXin
 * @date 2021/12/28 18:59
 */
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 唯一 id
        System.out.println("[handlerRemoved] " + ctx.channel().id().asLongText());
        System.out.println("[handlerRemoved] " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 唯一 id
        System.out.println("[handlerAdded] " + ctx.channel().id().asLongText());
        System.out.println("[handlerAdded] " + ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 接受消息
        System.out.println("收到消息：" + textWebSocketFrame.text());

        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[服务器] - " + LocalDateTime.now()
        + " " + textWebSocketFrame.text()));
    }
}
