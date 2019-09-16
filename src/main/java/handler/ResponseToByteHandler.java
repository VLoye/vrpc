package handler;
/**
 * Created by VLoye on 2019/8/30.
 */

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import message.RpcResponseMessage;
import service.AbstractService;

/**
 * @author V
 * @Classname ResponseToByteHandler
 * @Description
 **/
public class ResponseToByteHandler extends MessageToByteEncoder<RpcResponseMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcResponseMessage rpcResponse, ByteBuf byteBuf) throws Exception {
        String jsonStr = JSONObject.toJSONString(rpcResponse);
        byteBuf.writeBytes(jsonStr.getBytes());
        byteBuf.writeBytes(AbstractService.splitDelimiter);
    }
}
