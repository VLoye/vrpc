package cn.v.vrpc.protocol;

import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface ICodec {
    void encode(ChannelHandlerContext context, Transportable rpcMessageFrame, ByteBuf out) throws CodecException;

    void decode(ChannelHandlerContext context, ByteBuf in, List<Object> rpcMessageFrames) throws CodecException;
}
