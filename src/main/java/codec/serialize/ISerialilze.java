package codec.serialize;

import java.io.IOException;

/**
 * Created by VLoye on 2019/9/10.
 */
public interface ISerialilze<T> {
    byte[] doSerialize(T t) throws IOException;
    T deSerialize(byte[] bytes) throws IOException, ClassNotFoundException;
}
