package core.message;
/**
 * Created by VLoye on 2019/8/8.
 */

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author V
 * @Classname RpcMessage
 * @Description
 **/
public class RpcMessage implements Serializable{
    protected transient static final AtomicInteger index = new AtomicInteger(10000);
    protected byte type;
    protected int id;
    protected String sessionId;
    protected boolean reply;


    public RpcMessage() {
    }

    public RpcMessage(Method method, Object[] args) {
    }


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public void fillAttributes(RpcRequestMessage msg) {
        this.sessionId = msg.getSessionId();
        this.id = msg.getId();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
