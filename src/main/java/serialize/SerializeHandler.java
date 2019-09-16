package serialize;
/**
 * Created by VLoye on 2019/9/10.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AbstractService;

/**
 * @author V
 * @Classname SerializeHandler
 * @Description
 **/
public class SerializeHandler extends MessageToByteEncoder<Object>{
    private static final Logger logger = LoggerFactory.getLogger(SerializeHandler.class);
    private ISerialilze serialilze;

    public SerializeHandler(ISerialilze serialilze) {
        this.serialilze = serialilze;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
        logger.info("entry SerializeHandler");
        //hearbeat or invocation request, else do nothing
        byte[] bytes = serialilze.doSerialize(o);
        logger.info(String.valueOf(bytes.length));
        byteBuf.writeBytes(bytes);
        byteBuf.writeBytes(Unpooled.wrappedBuffer(AbstractService.splitDelimiter));
//        ctx.writeAndFlush(o);
    }
}
