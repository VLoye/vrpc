package core.message;
/**
 * Created by VLoye on 2019/8/26.
 */

/**
 * @author V
 * @Classname RpcRequestMessage
 * @Description
 **/
public class RpcRequestMessage extends RpcMessage {
    protected String className;
    protected String method;
    protected Class<?>[] paramTypes;
    protected Object[] args;

    public RpcRequestMessage() {
        super();
        this.id = index.getAndIncrement();
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
