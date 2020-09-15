package cn.v.vrpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * v
 * 2019/12/16 下午10:13
 * 1.0
 */
public interface ConnectionEventListener {

    void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception;


    void channelRegistered(ChannelHandlerContext ctx) throws Exception;


    void channelUnregistered(ChannelHandlerContext ctx) throws Exception;


    void channelActive(ChannelHandlerContext ctx) throws Exception;


    void channelInactive(ChannelHandlerContext ctx) throws Exception;


    void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;


    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}
