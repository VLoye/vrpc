package cn.v.vrpc.protocol.constant;

public enum MessageVersionEnum {
    V1((byte)0x01);

    private byte value;

    MessageVersionEnum(byte value) {
        this.value = value;
    }
}
