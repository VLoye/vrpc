package codec.serialize;
/**
 * Created by VLoye on 2019/9/10.
 */

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author V
 * @Classname JDKSerialize
 * @Description
 **/
public class JDKSerialize implements ISerialilze<Object> {

    @Override
    public byte[] doSerialize(Object o) throws IOException {
        ByteOutputStream bos = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(o);
        byte[] bytes = bos.getBytes();
        return bos.getBytes();
    }

    @Override
    public Object deSerialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteInputStream bis = new ByteInputStream(bytes,bytes.length);
        ObjectInputStream ois = new ObjectInputStream(bis);
//        bis.read(bytes);
        Object o = ois.readObject();
        return o;
    }
}
