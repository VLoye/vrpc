package codec;
/**
 * Created by VLoye on 2019/8/18.
 */

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import message.RpcMessage;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author V
 * @Classname RpcDecoder
 * @Description
 **/
public class RpcDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readableBytes();
        byte[] bytes  = new byte[length];
        byteBuf.readBytes(bytes);
        String jsonStr = new String(bytes, Charset.forName("UTF-8"));
        List req= JSONObject.parseArray(jsonStr, RpcMessage.class);
        list.add(req.get(0));
    }
}
