package com.sugar.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 功能描述: HttpHandler 处理器
 *
 * @author XiaoNianXin
 * @date 2021/12/26 17:00
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据
     * @param ctx
     * @param httpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        // 判断 msg 是不是一个 request 请求
        if (httpObject instanceof HttpRequest) {
            System.out.println("管道哈希值：" + ctx.pipeline().hashCode() + " 处理器哈希值：" + this.hashCode());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            // 请求过滤
            HttpRequest request = (HttpRequest) httpObject;
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("此请求被过滤");
                return;
            }

            // 回复浏览器 [回复客户端 - http协议]
            ByteBuf buf = Unpooled.copiedBuffer("Hello Netty", CharsetUtil.UTF_8);

            // 构造 Http 协议 httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());

            // 发送
            ctx.writeAndFlush(response);
        }
    }
}
