package cn.v.vrpc.protocol.constant;

public enum MessageCodecEnum {
    JDK((byte)0x01),
    HESSIAN((byte)0x02),
    ;


    private byte value;

    MessageCodecEnum(byte value) {
        this.value = value;
    }
}
