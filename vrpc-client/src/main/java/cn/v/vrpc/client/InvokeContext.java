package cn.v.vrpc.client;

import cn.v.vrpc.client.rpc.ServiceManager;
import cn.v.vrpc.protocol.ISerializer;
import cn.v.vrpc.protocol.ProtocolCode;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InvokeContext implements Serializable {
    private ChannelHandlerContext ctx;

    private ProtocolCode protocolCode = ProtocolCode.formName("rpc");
    private byte version = 0x01;
    // req or req_one_way or ?ctrl?
    private byte type = 0x01;
    private byte serializer = 0x1;
    private long timeout;
    private boolean crc = true;
    private String sessionId;

    private ServiceManager serviceManager;



    private ConcurrentHashMap<String, Object> properties = new ConcurrentHashMap<>();


    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public byte getProtocolCode() {
        return protocolCode.code();
    }

    public void setProtocolCode(ProtocolCode protocolCode) {
        this.protocolCode = protocolCode;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getSerializer() {
        return serializer;
    }

    public void setSerializer(byte serializer) {
        this.serializer = serializer;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isCrc() {
        return crc;
    }

    public void setCrc(boolean crc) {
        this.crc = crc;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ConcurrentHashMap<String, Object> getProperties() {
        return properties;
    }

    public Object get(String key) {
        if (null == key) {
            return null;
        }
        return properties.get(key);
    }

    public Object get(String key, Object o) {
        if (null == key || null == properties.get(key)) {
            return o;
        }
        return properties.get(key);
    }

    public String getAsString(String key) {
        if (null == key) {
            return null;
        }
        return (String) properties.get(key);
    }

    public String getAsString(String key, String s) {
        return getAsString(key) == null ? s : getAsString(key);
    }



    public void put(String key, Object o) {
        properties.put(key, o);
    }

    public void putAll(Map map) {
        properties.putAll(map);
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
