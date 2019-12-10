package cn.v.vrpc.protocol.codec;

import cn.v.vrpc.protocol.MessageFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class JDKCodec extends AbstractCodec {
    private static final Logger logger = LoggerFactory.getLogger(JDKCodec.class);

    @Override
    public void encode(ChannelHandlerContext context, MessageFrame messageFrame, ByteBuf out) {
        out.writeByte(messageFrame.getMagic());
        out.writeByte(messageFrame.getVersion());
        out.writeByte(messageFrame.getType());
        out.writeByte(messageFrame.getSwitchOption());
        out.writeBytes(messageFrame.getSessionId().getBytes(), 0, 16);
        out.writeShort(messageFrame.getTimeout());
        out.writeByte(messageFrame.getProtocol());
        out.writeByte(messageFrame.getCodec());
        doSerializer(messageFrame.getHeader(),out);
        doSerializer(messageFrame.getBody(),out);
        out.markReaderIndex();
        byte[] bytes = new byte[out.readableBytes()];
        out.readBytes(bytes);

        //crc


    }


    @Override
    public void decode(ChannelHandlerContext context, ByteBuf in, List<MessageFrame> messageFrames) {

    }
}
