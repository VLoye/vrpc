package cn.v.vrpc.client;
/**
 * Created by VLoye on 2019/8/26.
 */

import java.io.Serializable;

/**
 * @author V
 * @Classname RpcRequestMessage
 * @Description
 **/
public class RpcRequestMessage implements Serializable {
    protected String className;
    protected String method;
    protected Class<?>[] paramTypes;
    protected Object[] args;

    public RpcRequestMessage(String className, String method, Class<?>[] paramTypes, Object[] args) {
        this.className = className;
        this.method = method;
        this.paramTypes = paramTypes;
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }
}
