package cn.v.vrpc.protocol.constant;

public enum MessageTypeEnum {
    REQUEST((byte)0x01),
    REQUEST_ONE_WAY((byte)0x02),
    RESPONSE((byte)0x03),
    CTRL((byte)0x04),
    CTRL_ONE_WAY((byte)0x05),
    ;

    private byte value;

    MessageTypeEnum(byte value) {
        this.value = value;
    }

}
