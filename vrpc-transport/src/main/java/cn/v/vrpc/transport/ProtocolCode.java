package cn.v.vrpc.transport;/**
 * Created by V on 2019/12/13.
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * V
 * 2019/12/13 14:50
 */
public class ProtocolCode {
    private static CopyOnWriteArrayList<ProtocolCode> protocols = new CopyOnWriteArrayList<>();
    private byte code;
    private String name;

    static {
        //add protocols define here.
        protocols.add(new ProtocolCode((byte) 0x01, "rpc"));
    }

    public ProtocolCode(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ProtocolCode fromCode(byte code) {
        return protocols.stream().filter(x -> x.code == code).findAny().orElse(null);
    }

    public static ProtocolCode formName(String name) {
        return protocols.stream().filter(x -> x.name.equals(name)).findAny().orElse(null);
    }

    public static void registerProtocol(ProtocolCode protocolCode){
        protocols.add(protocolCode);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProtocolCode that = (ProtocolCode) o;

        return code == that.code;
    }

    @Override
    public int hashCode() {
        return (int) code;
    }

    @Override
    public String toString() {
        return "ProtocolCode{" +
                "code=" + code +
                '}';
    }
}
