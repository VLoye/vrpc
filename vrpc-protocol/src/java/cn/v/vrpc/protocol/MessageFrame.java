package cn.v.vrpc.protocol;

//        通用私有协议设计 ***
//        0     1       2    3      4                    18      20     22       23     24
//        +-----+-------+----+------+--------------------+-------+-------+--------+-----+
//        |magic|version|type|switch|        sessionId           |timeout|protocol|codec|
//        +-----+-------+----+------+----------------------------+-------+--------+-----+
//        |   header  ...           |       body  ...                    |     crc      +
//        +-----+-------+----+------+--------------------+-------+-------+--------+-----+

public class MessageFrame {
    private byte magic;
    private byte version;
    private byte type;
    private byte switchOption;
    private String sessionId;
    private short timeout;
    private byte protocol;
    private byte codec;
    private Object header;
    private Object body;
    private int crc;

    public MessageFrame(byte magic, byte version, byte type, byte switchOption, String sessionId,
                        short timeout, byte protocol, byte codec, Object header, Object body) {
        this.magic = magic;
        this.version = version;
        this.type = type;
        this.switchOption = switchOption;
        this.sessionId = sessionId;
        this.timeout = timeout;
        this.protocol = protocol;
        this.codec = codec;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public byte getCodec() {
        return codec;
    }

    public void setCodec(byte codec) {
        this.codec = codec;
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
}
