package cn.v.vrpc.protocol.codec;

import cn.v.vrpc.protocol.CodecException;
import cn.v.vrpc.protocol.MessageFrame;
import cn.v.vrpc.protocol.constant.RpcComstant;
import cn.v.vrpc.protocol.constant.RpcProtocolV1;
import cn.v.vrpc.protocol.utils.CrcUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RpcProtocolCodec extends AbstractCodec {
    private static final Logger logger = LoggerFactory.getLogger(RpcProtocolCodec.class);

    @Override
    public void encode(ChannelHandlerContext context, MessageFrame messageFrame, ByteBuf out) throws CodecException {
        out.writeByte(messageFrame.getMagic());
        out.writeByte(messageFrame.getVersion());
        out.writeByte(messageFrame.getType());
        out.writeByte(messageFrame.getSwitchOption());
        out.writeBytes(messageFrame.getSessionId().getBytes(), 0, 16);
        out.writeShort(messageFrame.getTimeout());
        out.writeByte(messageFrame.getProtocol());
        out.writeByte(messageFrame.getCodec());
        doSerializer(messageFrame.getHeader(), out);
        doSerializer(messageFrame.getBody(), out);
        out.markReaderIndex();
        byte[] bytes = new byte[out.readableBytes()];
        out.readBytes(bytes);
        out.writeInt(CrcUtil.evalCrc32(bytes));
        out.resetReaderIndex();
        //crc

    }


    @Override
    public void decode(ChannelHandlerContext context, ByteBuf in, List<MessageFrame> messageFrames) throws CodecException {
        if (in.readableBytes() < RpcComstant.BASE_LENGTH) {
            throw new CodecException("the length is shorter than base length 24");
        }
        in.markReaderIndex();
        byte magic = in.readByte();
        if (magic != RpcComstant.MAGIC) {
            throw new CodecException("Unknown message.");
        }
        byte version = in.readByte();
        if (version == RpcProtocolV1.PROTOCOL_VERSION_1) {
            byte type = in.readByte();
            byte switchOption = in.readByte();
            byte[] sessionId = new byte[16];
            in.readBytes(sessionId);
            short timeout = in.readShort();
            byte protocol = in.readByte();
            byte codec = in.readByte();
            Object head = null;
            Object body = null;
            int crc = 0;
            if (in.readableBytes() > 4) {
                int headLen = in.readInt();
                byte[] headBytes = new byte[headLen];
                in.readBytes(headBytes);
            }else {
                in.resetReaderIndex();
                return;
            }
            if(in.readableBytes()>4){
                int bodyLen = in.readInt();
                byte[] bodyBytes = new byte[bodyLen];
                in.readBytes(bodyBytes);
            }else {
                in.resetReaderIndex();
                return;
            }
            if(in.readableBytes()>=4){
                crc = in.readInt();
            }else{
                in.resetReaderIndex();
                return;
            }


        }else {
            throw new CodecException("Unknown Version: "+version);
        }


    }

}
