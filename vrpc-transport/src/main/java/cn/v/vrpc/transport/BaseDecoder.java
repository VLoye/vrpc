package cn.v.vrpc.transport;/**
 * Created by V on 2019/12/13.
 */

import cn.v.vrpc.protocol.CodecException;
import cn.v.vrpc.protocol.IProtocol;
import cn.v.vrpc.protocol.rpc.RpcComstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * V
 * 2019/12/13 14:08
 */
public class BaseDecoder extends AbstractBaseDecoderHandler {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() > 2) {
            byte magic = in.readByte();
            if (!checkMagic(magic)) {
                throw new CodecException("Unknown message: " + magic);
            }
            byte protocolByte = in.readByte();
            ProtocolCode protocolCode = ProtocolCode.fromCode(protocolByte);
            IProtocol protocol = ProtocolManager.getProtocol(protocolCode);
            if (protocol != null) {
                in.resetReaderIndex();
                protocol.getCodec().decode(ctx, in, out);
            }
        }

    }

    private boolean checkMagic(byte magic) {
        return RpcComstant.MAGIC == magic;
    }
}
