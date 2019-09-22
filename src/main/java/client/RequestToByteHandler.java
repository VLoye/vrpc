package client;
/**
 * Created by VLoye on 2019/9/5.
 */

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import core.message.RpcRequestMessage;
import server.service.AbstractService;

import java.io.Serializable;

/**
 * @author V
 * @Classname RequestToByteHandler
 * @Description
 **/
public class RequestToByteHandler extends MessageToByteEncoder<RpcRequestMessage> implements Serializable{


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRequestMessage msg, ByteBuf byteBuf) throws Exception {
        String json = JSONObject.toJSONString(msg);
        byteBuf.writeBytes(json.getBytes());
        byteBuf.writeBytes(AbstractService.splitDelimiter);
        channelHandlerContext.writeAndFlush(byteBuf);
    }
}
