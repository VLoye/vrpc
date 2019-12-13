package cn.v.vrpc.protocol;

import java.util.concurrent.ExecutorService;

public interface IProtocol {
    ICodec getCodec();
    ExecutorService getProcesssor();
}
