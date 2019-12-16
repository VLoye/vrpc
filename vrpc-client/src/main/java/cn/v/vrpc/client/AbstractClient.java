package cn.v.vrpc.client;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * v
 * 2019/12/16 下午9:53
 * 1.0
 */
public abstract class AbstractClient extends AbstractLifeCycle implements IClient {
    private URLParser praser;
    private ConnectionManager connectionManager;
    private ConnectionFactory connectionFactory;
    private ConnectionSelectStrategy connectionSelectStrategy;
    private ReconnectionTrigger reconnectionTrigger;

    private HeartbeatTrigger heartbeatTrigger;

    private ConnectionEventListener connectionEventListener;

    private Map<CommandCode, ExecutorService> userProcessors;

    protected boolean isStartup(){
        return isStartup.get() == true;
    }

    @Override
    public Object syncInvoke(URL url, Object message) {
        return null;
    }

    @Override
    public Object syncInvoke(URL url, InvokeContext context, Object body) {
        return null;
    }

    @Override
    public Object syncInvoke(InvokeContext context, Object body) {
        return null;
    }

    @Override
    public ResponseFuture callWithFuture(URL url, Object message) {
        return null;
    }

    @Override
    public ResponseFuture callWithFuture(URL url, InvokeContext context, Object body) {
        return null;
    }

    @Override
    public ResponseFuture callWithFuture(InvokeContext context, Object body) {
        return null;
    }

    @Override
    public void callWithCallback(URL url, Object message) {

    }

    @Override
    public void callWithCallback(URL url, InvokeContext context, Object body) {

    }

    @Override
    public void callWithCallback(InvokeContext context, Object body) {

    }

    @Override
    public Connection getConn(URL url) {
        return null;
    }
}
