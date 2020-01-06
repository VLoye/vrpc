package cn.v.vrpc.client;

/**
 * v
 * 2020/1/6 下午11:54
 * 1.0
 */
public class RpcProtocolV1MessageFactory implements MessageFactory<Object> {
    @Override
    public Object request() {
        return null;
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
