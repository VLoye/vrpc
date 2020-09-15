package cn.v.vrpc.protocol.codec;

import cn.v.vrpc.protocol.*;
import cn.v.vrpc.protocol.rpc.RpcComstant;
import cn.v.vrpc.protocol.rpc.RpcProtocolV1;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import cn.v.vrpc.protocol.serializer.SerializerException;
import cn.v.vrpc.protocol.serializer.SerializerFactory;
import cn.v.vrpc.protocol.utils.CrcUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RpcProtocolCodec extends AbstractCodec {
    private static final Logger logger = LoggerFactory.getLogger(RpcProtocolCodec.class);

    @Override
    public void encode(ChannelHandlerContext context, Transportable transportableMsg, ByteBuf out) throws CodecException {
        if (transportableMsg instanceof RpcMessageFrame) {
            RpcMessageFrame rpcMessageFrame = (RpcMessageFrame) transportableMsg;
            out.writeByte(rpcMessageFrame.getMagic());
            out.writeByte(rpcMessageFrame.getProtocol());
            out.writeByte(rpcMessageFrame.getVersion());
            out.writeByte(rpcMessageFrame.getType());
            out.writeByte(rpcMessageFrame.getSwitchOption());
            out.writeShort(rpcMessageFrame.getTimeout());
            out.writeByte(rpcMessageFrame.getSerializerType());
            out.writeBytes(rpcMessageFrame.getId().getBytes(), 0, 16);
            doSerializer(rpcMessageFrame.getHeader(), rpcMessageFrame.getBody(), out);
            out.markReaderIndex();
            byte[] bytes = new byte[out.readableBytes()];
            out.readBytes(bytes);
            out.writeInt(CrcUtil.evalCrc32(bytes));
            out.resetReaderIndex();
            //crc
        }
    }


    @Override
    public void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws CodecException {
        if (in.readableBytes() < RpcComstant.BASE_LENGTH) {
            throw new CodecException("message length is shorter than base length: " + RpcComstant.BASE_LENGTH);
        }
        in.markReaderIndex();
        byte magic = in.readByte();
        if (magic != RpcComstant.MAGIC) {
            throw new CodecException("Unknown message.");
        }
        byte protocol = in.readByte();
        byte version = in.readByte();
        if (version == RpcProtocolV1.PROTOCOL_VERSION_1) {
            byte type = in.readByte();
            byte switchOption = in.readByte();
            short timeout = in.readShort();
            byte serializeType = in.readByte();
            byte[] sessionId = new byte[16];
            in.readBytes(sessionId);
            short headLen = 0;
            short bodyLen = 0;
            byte[] headBytes = new byte[0];
            byte[] bodyBytes = new byte[0];
            Object head = null;
            Object body = null;
            int crc = 0;
            if (in.readableBytes() > 4) {   //headLen + bodyLen
                headLen = in.readShort();
                bodyLen = in.readShort();
            } else {
                in.resetReaderIndex();
                return;
            }
            if (in.readableBytes() > headLen + bodyLen) {
                headBytes = new byte[headLen];
                in.readBytes(headBytes);
                bodyBytes = new byte[bodyLen];
                in.readBytes(bodyBytes);
            } else {
                in.resetReaderIndex();
                return;
            }
            if (in.readableBytes() >= 4) {
                crc = in.readInt();
            } else {
                in.resetReaderIndex();
                return;
            }
            in.resetReaderIndex();
            byte[] bytes = new byte[RpcComstant.BASE_LENGTH + headLen + bodyLen];
            in.readBytes(bytes);
            //consume four byte of crc
            in.readInt();
            if (!CrcUtil.verCrc32(bytes, crc)) {
                throw new CodecException("crc verification error");
            }
            ISerializer serializerUtil = SerializerFactory.getSerializerById(serializeType);
            try {
                head = serializerUtil.deSerialize(headBytes);
                body = serializerUtil.deSerialize(bodyBytes);
            } catch (SerializerException e) {
                throw new CodecException(e);
            }

            RpcMessageFrame frame = new RpcMessageFrame(magic, protocol, version, type, switchOption, timeout, serializeType, new String(sessionId), head, body);
            out.add(frame);
            logger.debug("receive a valid message[{}] form channel[{}].", new String(sessionId),context.channel().id().asShortText());
        } else {
            throw new CodecException("Unknown Version: " + version);
        }


    }

}
