package client;
/**
 * Created by VLoye on 2019/9/3.
 */

import client.core.ClientConfig;
import client.handler.CHeartbeatHandler;
import client.handler.ResponseHandler;
import com.sun.xml.internal.bind.v2.TODO;
import common.RpcUtil;
import core.ILifeCycle;
import core.IService;
import core.LifeCycleException;
import core.ServiceStatus;
import core.config.HearBeatConfig;
import core.config.ServiceConfig;
import core.exc.InitializationException;
import core.exc.RequestTimeoutException;
import core.exc.RpcException;

import core.message.RpcMessage;
import core.message.RpcRequestMessage;
import io.netty.handler.timeout.IdleStateHandler;
import org.eclipse.core.internal.events.ILifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author V
 * @Classname RpcClient
 * @Description
 **/
public class RpcClient implements ILifeCycle {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private ConnManager manager;
    private ClientConfig config;
    private ServiceStatus status = ServiceStatus.NEW;

    public RpcClient(ClientConfig config) {
        this.config = config;
    }

    public ResponseFuture call(RpcRequestMessage message) throws RpcException {
        IConnection connection = manager.getConn();
        return connection.call(message);
    }


    public ResponseFuture call(RpcRequestMessage message, long timeout) throws RpcException {
        long startTime = System.currentTimeMillis();
        long fetchConnectionTime = 0L;
        IConnection connection = null;
        try {
            connection = manager.getConn(timeout);
            fetchConnectionTime = System.currentTimeMillis();
            logger.debug("Request Message[sessionId = {}] fetch a connection[{channelId = {}}]", message.getSessionId(), connection.getChannel().id().asShortText());
            return connection.call(message);
        } catch (RequestTimeoutException e) {
            throw new RpcException(e);
        } finally {
            recycle(connection);
        }

    }

    private void recycle(IConnection connection) {
        connection.setBusy(false);
        manager.recycle(connection);
    }


    public static void main(String[] args) throws NoSuchMethodException, InterruptedException, ExecutionException, TimeoutException, RpcException {
/*

        ClientConfig clientConfig = ClientConfig.def().build();

        RpcClient client = new RpcClient(clientConfig);
        try {
            client.start();
        } catch (LifeCycleException e) {
            e.printStackTrace();
        }
        RpcRequestMessage message = new RpcRequestMessage();
        message.setClassName("client.AService");
        message.setParamTypes(new Class[]{String.class});
        message.setArgs(new Object[]{"abcd"});
        message.setMethod(AService.class.getDeclaredMethod("dothing", String.class).getName());
        message.setSessionId(RpcUtil.UUID());
        message.setReply(true);
        ResponseFuture future = client.call(message,30000);
        Object o = future.get(30000, TimeUnit.MILLISECONDS);
        System.out.println(o);
*/

//        latch.await();

        IInterface service = RpcProxyFactory.proxy(IInterface.class);
        String result = service.dothing("aaaaa");
        System.out.println(result);
        System.out.println(service.toString());
    }

    @Override
    public void init() throws LifeCycleException {
        // TODO: 2019/11/27
        manager = new ConnManager(config);
        manager.init();
        status = ServiceStatus.INIT;
    }

    @Override
    public void start() throws LifeCycleException {
        initIfNeed();
        manager.start();
        status = ServiceStatus.STAER;
    }

    private void initIfNeed() throws LifeCycleException {
        if (status.equals(ServiceStatus.NEW)) {
            init();
        }
    }

    @Override
    public void close() throws LifeCycleException {

    }

    public ClientConfig getConfig() {
        return config;
    }
}

