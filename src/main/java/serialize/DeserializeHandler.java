package serialize;
/**
 * Created by VLoye on 2019/9/10.
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author V
 * @Classname DeserializeHandler
 * @Description
 **/
public class DeserializeHandler extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(DeserializeHandler.class);
    private ISerialilze serialilze;

    public DeserializeHandler(ISerialilze serialilze) {
        this.serialilze = serialilze;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        logger.info("entry DeserializeHandler");
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        logger.info(String.valueOf(len));
        byteBuf.readBytes(bytes);
        Object o = serialilze.deSerialize(bytes);
        list.add(o);
    }
}
