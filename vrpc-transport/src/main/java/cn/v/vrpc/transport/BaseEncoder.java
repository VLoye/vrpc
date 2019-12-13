package cn.v.vrpc.transport;/**
 * Created by V on 2019/12/13.
 */

import cn.v.vrpc.protocol.CodecException;
import cn.v.vrpc.protocol.IProtocol;
import cn.v.vrpc.protocol.Transportable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author: V
 * 2019/12/13 14:09
 * 1.0.0
 */
public class BaseEncoder extends MessageToByteEncoder<Transportable> {
    private static final AttributeKey<ProtocolCode> KEY_PROTOCOL = AttributeKey.newInstance("protocol");

    private ProtocolCode defaultProtocolCode;

    public BaseEncoder(ProtocolCode protocolCode) {
        super();
        this.defaultProtocolCode = protocolCode;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Transportable msg, ByteBuf out) throws Exception {
        Attribute<ProtocolCode> attr = ctx.channel().attr(KEY_PROTOCOL);
        ProtocolCode protocolCode;
        if (attr == null || attr.get() == null) {
            protocolCode = defaultProtocolCode;
        } else {
            protocolCode = attr.get();
        }
        IProtocol protocol = ProtocolManager.getProtocol(protocolCode);
        if (protocol != null) {
            protocol.getCodec().encode(ctx, msg, out);
        } else {
            throw new CodecException("not such protocol: " + protocol);
        }
    }
}
