package codec;
/**
 * Created by VLoye on 2019/8/18.
 */

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import message.RpcMessage;

/**
 * @author V
 * @Classname RpcEncoder
 * @Description
 **/
public class RpcEncoder extends MessageToByteEncoder<RpcMessage>{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        String jsonStr = JSONObject.toJSONString(rpcMessage);
        byte[] bytes = jsonStr.getBytes();
        byteBuf.writeBytes(bytes);
    }
}
