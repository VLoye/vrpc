package handler;
/**
 * Created by VLoye on 2019/8/26.
 */

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.RpcRequestMessage;

/**
 * @author V
 * @Classname StringToRequestHandler
 * @Description
 **/
public class StringToRequestHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(s);
        RpcRequestMessage message = JSONObject.toJavaObject(jsonObject, RpcRequestMessage.class);
        ctx.fireChannelRead(message);
    }
}
