package cn.v.vrpc.client.rpc;

import java.io.Serializable;

/**
 * v
 * 2020/1/13 下午10:39
 * 1.0
 */
public class RpcResponse implements Serializable {
    private Object result;
    private boolean isSuccess;
    private Throwable cause;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
