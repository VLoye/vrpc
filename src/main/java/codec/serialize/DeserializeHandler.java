package codec.serialize;
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
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
//        logger.info(new String(bytes));
        Object o = null;
        try{
            o = serialilze.deSerialize(bytes);
        }catch (Exception e){
            logger.warn("Unknown Message format, discard it.");
            byteBuf.clear();
        }
        list.add(o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }
}
