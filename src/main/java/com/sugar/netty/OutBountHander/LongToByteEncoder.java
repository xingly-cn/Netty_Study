package com.sugar.netty.OutBountHander;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * 自定义编码器 - 客户端出站
 */
public class LongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("LongToByte 执行");
        byteBuf.writeLong(aLong);
    }
}
