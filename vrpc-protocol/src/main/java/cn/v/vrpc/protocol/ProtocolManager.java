package cn.v.vrpc.protocol;/**
 * Created by V on 2019/12/13.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * V
 * 2019/12/13 14:52
 */
public class ProtocolManager {
    //todo  need concurrent? let me think a bit about it...
    private static Map<ProtocolCode,IProtocol> protocolMap = new HashMap<ProtocolCode,IProtocol>();
    static {
        protocolMap.put(new ProtocolCode((byte) 0x01, "rpc"), new RpcProtocol());
    }

    public static void registerProtocol(ProtocolCode code, IProtocol protocol){
        protocolMap.put(code,protocol);
    }

    public static IProtocol getProtocol(ProtocolCode code){
        return protocolMap.get(code);
    }

    public static IProtocol removeProtocol(ProtocolCode code){
        return protocolMap.remove(code);
    }
}
