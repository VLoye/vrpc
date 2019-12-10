package cn.v.vrpc.protocol.constant;

public enum MessageProtocolEnum {
    BINARY((byte)0x01),
    SERIALIZER((byte)0x02),
    ;


    private byte value;

    MessageProtocolEnum(byte value) {
        this.value = value;
    }
}
