package cn.v.vrpc.protocol.codec;

import cn.v.vrpc.protocol.ICodec;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import cn.v.vrpc.protocol.utils.CloseableUtil;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCodec implements ICodec {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);
    protected List<RpcMessageFrame> rpcMessageFrames = new ArrayList<>();


    protected void doSerializer(Object header, Object body, ByteBuf out) {
        ByteArrayOutputStream headerBos = null;
        ObjectOutputStream headerOos = null;
        byte[] headerBytes = new byte[0];
        ByteArrayOutputStream bodyBos = null;
        ObjectOutputStream bodyOos = null;
        byte[] bodyBytes = new byte[0];

        try {
            if (header != null) {
                headerBos = new ByteArrayOutputStream();
                headerOos = new ObjectOutputStream(headerBos);
                headerOos.writeObject(header);
                headerBytes = headerBos.toByteArray();
            }
            if (body != null) {
                bodyBos = new ByteArrayOutputStream();
                bodyOos = new ObjectOutputStream(bodyBos);
                bodyOos.writeObject(body);
                bodyBytes = bodyBos.toByteArray();
            }
            out.writeShort(headerBytes.length);
            out.writeShort(bodyBytes.length);
            out.writeBytes(headerBytes);
            out.writeBytes(bodyBytes);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            CloseableUtil.safeRelease(headerBos, headerOos, bodyBos, bodyOos);
        }

    }
}
