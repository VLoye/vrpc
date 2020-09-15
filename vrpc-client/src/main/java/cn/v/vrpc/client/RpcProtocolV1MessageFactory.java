package cn.v.vrpc.client;

import cn.v.vrpc.protocol.rpc.RpcMessageFrame;

/**
 * v
 * 2020/1/6 下午11:54
 * 1.0
 */
public class RpcProtocolV1MessageFactory implements MessageFactory<Object> {
    @Override
    public Object request() {
        RpcRequest message = new RpcRequest("className","method",new Class[]{Integer.class},new Object[]{"params"});
        return message;
    }

    @Override
    public Object response() {
        return null;
    }

    @Override
    public Object ping() {
        RpcMessageFrame frame = new RpcMessageFrame();
        frame.setMagic((byte)0x01);
        frame.setProtocol((byte)0x01);
        frame.setVersion((byte)0x01);
        frame.setSwitchOption((byte)1);
        frame.setTimeout((short)0);
        frame.setSerializerType((byte)1);
        frame.setId(RpcUtil.UUID());
        frame.setType((byte)0xfe);
        return frame;
    }

    @Override
    public Object pong() {
        RpcMessageFrame frame = new RpcMessageFrame();
        frame.setMagic((byte)0x01);
        frame.setProtocol((byte)0x01);
        frame.setVersion((byte)0x01);
        frame.setSwitchOption((byte)1);
        frame.setTimeout((short)0);
        frame.setSerializerType((byte)1);
        frame.setId(RpcUtil.UUID());
        frame.setType((byte)0xff);
        return frame;
//        return "pong";
    }
}
