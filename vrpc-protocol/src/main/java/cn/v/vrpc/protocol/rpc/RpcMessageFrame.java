package cn.v.vrpc.protocol.rpc;

import cn.v.vrpc.protocol.*;

/**
 * 通用私有协议设计 ***
 * REQUEST / RESPONSE
 * 0        1        2        4        5        6       7       8       9      10       11      12     13       14     15      16
 * +--------+--------+--------+--------+--------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+
 * |  magic |protocol|version |  type  | switch |timeout|                       sessionId (16)     ...                          |
 * +--------+--------+--------+--------+--------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+
 * |                     ...                            |    headLen    |    bodyLen    |                ...                    |
 * +--------+--------+--------+--------+--------+-------+-------+-------+-------+-------+                                       +
 * |                                                ...                                                                         |
 * +                              head                   +                    body                                              +
 * |                                               ...                                                          |CRC32(optional)|
 * +--------+--------+--------+--------+--------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+
 */


public class RpcMessageFrame extends AbstractMessage implements Transportable {
    private byte magic;
    private byte version;
    private byte type;
    private byte switchOption;
    private String id;
    private short timeout;
    private byte protocol;
    private byte serializerType;
    private Object header;
    private Object body;
    private int crc;


    public static RpcMessageFrame REQ() {
        return new RpcMessageFrame(RpcComstant.MAGIC, (byte) 0, (byte) 0, Type.REQ, (byte) 0, (short) 0, (byte) 0, null, null, null);
    }

    public static RpcMessageFrame RSP() {
        return new RpcMessageFrame(
                RpcComstant.MAGIC,
                (byte) 0x01,
                (byte) 0x01,
                Type.RSP,
                (byte) 0x01,
                (short) 0,
                (byte) 0x01,
                null,
                null,
                null);
    }

    public RpcMessageFrame() {
    }


    public RpcMessageFrame(byte magic, byte protocol, byte version, byte type, byte switchOption, short timeout, byte serializerType, String id,
                           Object header, Object body) {
        this.magic = magic;
        this.version = version;
        this.type = type;
        this.switchOption = switchOption;
        this.id = id;
        this.timeout = timeout;
        this.protocol = protocol;
        this.serializerType = serializerType;
        this.header = header;
        this.body = body;
    }

    public byte getMagic() {
        return magic;
    }

    public void setMagic(byte magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getSwitchOption() {
        return switchOption;
    }

    public void setSwitchOption(byte switchOption) {
        this.switchOption = switchOption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getTimeout() {
        return timeout;
    }

    public void setTimeout(short timeout) {
        this.timeout = timeout;
    }

    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

    public byte getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(byte serializerType) {
        this.serializerType = serializerType;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }


    @Override
    public String toString() {
        return "RpcMessageFrame{" +
                "magic=" + magic +
                ", version=" + version +
                ", type=" + type +
                ", switchOption=" + switchOption +
                ", sessionId='" + id + '\'' +
                ", timeout=" + timeout +
                ", protocol=" + protocol +
                ", codec=" + serializerType +
                ", header=" + header +
                ", body=" + body +
                ", crc=" + crc +
                '}';
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public ProtocolCode protocol() {
        return ProtocolCode.fromCode(protocol);
    }

    public static class Type {
        public static byte REQ = 0x01;
        public static byte REQ_ONE_WAY = 0x02;
        public static byte RSP = 0x03;
        public static byte CTRL = 0x04;
    }

}
