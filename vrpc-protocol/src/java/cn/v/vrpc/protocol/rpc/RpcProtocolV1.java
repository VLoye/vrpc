package cn.v.vrpc.protocol.rpc;

import cn.v.vrpc.protocol.ICodec;
import cn.v.vrpc.protocol.ProtocolFactory;
import cn.v.vrpc.protocol.ProtocolHandler;
import cn.v.vrpc.protocol.codec.RpcProtocolCodec;

public class RpcProtocolV1 {
    public static final byte PROTOCOL_VERSION_1 = 0x01;

    private ICodec codec ;
    private ProtocolHandler handler;
    private ProtocolFactory factory;

    public RpcProtocolV1() {
        this.codec = new RpcProtocolCodec();
    }
}
