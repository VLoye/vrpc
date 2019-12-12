package cn.v.vrpc.protocol.codec;

import cn.v.vrpc.protocol.ICodec;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import cn.v.vrpc.protocol.utils.CloseableUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCodec implements ICodec {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);
    protected List<RpcMessageFrame> rpcMessageFrames = new ArrayList<>();


    protected void doSerializer(Object o, ByteBuf out) {
        if(o == null){
            out.writeShort(0);
            return;
        }
        ByteOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            byte[] bytes = bos.getBytes();
            out.writeShort(bytes.length);
            out.writeBytes(bytes);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            CloseableUtil.safeRelease(bos, oos);
        }
    }
}
