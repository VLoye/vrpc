package cn.v.vrpc.protocol.serializer;

import cn.v.vrpc.protocol.ISerializer;

public class SerializerFactory {

    private static JDkSerializer jDkSerializer  = new JDkSerializer();

    public static ISerializer getSerializerById(byte flag){
        switch (flag){
            case 0x01:
                return jDkSerializer;
            default:
                return jDkSerializer;
        }
    }

}
