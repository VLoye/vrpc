package cn.v.vrpc.protocol;

import cn.v.vrpc.protocol.serializer.SerializerException;

public interface ISerializer {

    byte[] doSerialize(Object o) throws SerializerException;

    Object deSerialize(byte[] bytes) throws SerializerException;

}
