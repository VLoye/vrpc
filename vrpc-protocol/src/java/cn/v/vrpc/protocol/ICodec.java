package cn.v.vrpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface ICodec {
    void encode(ChannelHandlerContext context, MessageFrame messageFrame, ByteBuf out);
    void decode(ChannelHandlerContext context, ByteBuf in, List<MessageFrame> messageFrames);
}
