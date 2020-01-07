package cn.v.vrpc.protocol;

import cn.v.vrpc.protocol.codec.RpcProtocolCodec;

import java.util.concurrent.*;


/**
 * v
 * 2020/1/7 下午11:55
 * 1.0
 */
public class RpcProtocol implements IProtocol {
    private RpcProtocolCodec protocolCodec = new RpcProtocolCodec();
    @Override
    public ICodec getCodec() {
        return protocolCodec;
    }

    @Override
    public ExecutorService getProcessor() {
        return new ThreadPoolExecutor(1,1,30000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(1024));
    }
}
