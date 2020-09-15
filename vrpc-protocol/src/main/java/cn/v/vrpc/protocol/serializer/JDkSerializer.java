package cn.v.vrpc.protocol.serializer;

import cn.v.vrpc.protocol.ISerializer;
import cn.v.vrpc.protocol.utils.CloseableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JDkSerializer implements ISerializer {
    private static final Logger logger = LoggerFactory.getLogger(JDkSerializer.class);

    @Override
    public byte[] doSerialize(Object o) throws SerializerException {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new SerializerException(e);
        } finally {
            CloseableUtil.safeRelease(bos, oos);
        }
    }

    @Override
    public Object deSerialize(byte[] bytes) throws SerializerException {
        if (bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes, 0, bytes.length);
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
