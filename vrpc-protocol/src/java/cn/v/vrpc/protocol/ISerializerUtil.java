package cn.v.vrpc.protocol;

public interface ISerializerUtil {
    byte[] doSerialize(Object o) throws SerializerException;

    Object deSerialize(byte[] bytes) throws SerializerException;

}
