package cn.v.vrpc.client;

/**
 * v
 * 2020/1/6 下午11:54
 * 1.0
 */
public class RpcProtocolV1MessageFactory implements MessageFactory<Object> {
    @Override
    public Object request() {
        RpcRequestMessage message = new RpcRequestMessage("className","method",new Class[]{Integer.class},new Object[]{"params"});
        return message;
    }

    @Override
    public Object response() {
        return null;
    }

    @Override
    public Object ping() {
        return "ping";
    }

    @Override
    public Object pong() {
        return "pong";
    }
}
