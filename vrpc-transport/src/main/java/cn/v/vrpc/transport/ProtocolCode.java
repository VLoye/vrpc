package cn.v.vrpc.transport;/**
 * Created by V on 2019/12/13.
 */

/**
 * V
 * 2019/12/13 14:50
 */
public class ProtocolCode {
    private byte code;

    public ProtocolCode(byte code) {
        this.code = code;
    }

    public static ProtocolCode fromCode(byte code){
        return new ProtocolCode(code);
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
