package handler;
/**
 * Created by VLoye on 2019/9/4.
 */

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.RpcResponseMessage;

/**
 * @author V
 * @Classname StringToResponseHandler
 * @Description
 **/
public class StringToResponseHandler extends SimpleChannelInboundHandler<String>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(s);
        RpcResponseMessage message = JSONObject.toJavaObject(jsonObject, RpcResponseMessage.class);
        ctx.fireChannelRead(message);
    }
}
