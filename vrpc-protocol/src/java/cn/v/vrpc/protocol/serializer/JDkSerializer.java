package cn.v.vrpc.protocol.serializer;

import cn.v.vrpc.protocol.ISerializer;
import cn.v.vrpc.protocol.utils.CloseableUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JDkSerializer implements ISerializer {
    @Override
    public byte[] doSerialize(Object o) throws SerializerException {
        ByteOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            byte[] bytes = bos.getBytes();
            return bytes;
        } catch (Exception e) {
            throw new SerializerException(e);
        } finally {
            CloseableUtil.safeRelease(bos, oos);
        }
    }

    @Override
    public Object deSerialize(byte[] bytes) throws SerializerException {
        ByteInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteInputStream(bytes, bytes.length);
            ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return o;
        } catch (Exception e) {
            throw new SerializerException(e);
        } finally {
            CloseableUtil.safeRelease(bis, ois);
        }
    }
}
