package cn.v.vrpc.client;


import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.protocol.rpc.RpcComstant;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import cn.v.vrpc.protocol.rpc.RpcProtocolV1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

/**
 * Implement for Remote transport client
 * v
 * 2019/12/16 下午9:53
 * 1.0
 */
public abstract class AbstractClient extends AbstractLifeCycle implements IClient {
    protected URLParser urlParser;
    protected ConnectionManager connectionManager;

    protected Map<CommandCode, ExecutorService> userProcessors;


    public AbstractClient() {
        this(new URLParser(),new ConnectionManager(new RandomSelectStrategy()));
    }

    public AbstractClient(URLParser urlParser, ConnectionManager connectionManager) {
        this(urlParser,connectionManager,null);
    }

    public AbstractClient(URLParser urlParser, ConnectionManager connectionManager, Map<CommandCode, ExecutorService> userProcessors) {
        this.urlParser = urlParser;
        this.connectionManager = connectionManager;
        this.userProcessors = userProcessors;
    }

    protected boolean isStartup() {
        return isStartup.get() == true;
    }

    @Deprecated
    @Override
    public Object syncInvoke(String url, Object message) throws RemotingException, InterruptedException {
        Connection connection = getConn(url);
        if (null == connection) {
            throw new RemotingException("Has not usable connection.");
        }
        RpcMessageFrame requestMessage = packageRequestMessage(message);
        return connection.syncInvoke(requestMessage);
    }

    @Override
    public Object syncInvoke(String url, InvokeContext context, Object request) throws RemotingException, TimeoutException, InterruptedException {
        Connection connection = getConn(url);
        if (null == connection) {
            throw new RemotingException("Has not usable connection.");
        }
        RpcMessageFrame requestMessage = packageRequestMessage(request, context);
        long timeout = context.getTimeout();
        return connection.syncInvoke(requestMessage, timeout);
    }

    @Override
    public Object syncInvoke(InvokeContext context, Object request) {
        return new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ResponseFuture callWithFuture(String url, Object request) throws RemotingException {
        Connection connection = getConn(url);
        if (null == connection) {
            throw new RemotingException("Has not usable connection.");
        }
        RpcMessageFrame requestMessage = packageRequestMessage(request);
        return connection.asyncInvoke(requestMessage);
    }

    @Override
    public ResponseFuture callWithFuture(String url, InvokeContext context, Object request) throws RemotingException {
        Connection connection = getConn(url);
        if (null == connection) {
            throw new RemotingException("Has not usable connection.");
        }
        RpcMessageFrame requestMessage = packageRequestMessage(request, context);
        long timeout = context.getTimeout();
        return connection.asyncInvoke(requestMessage, timeout);
    }

    @Override
    public ResponseFuture callWithFuture(InvokeContext context, Object body) {
        throw new UnsupportedOperationException();
    }


//    @Override
//    public void callWithCallback(URL url, Object message) {
//
//    }
//
//    @Override
//    public void callWithCallback(URL url, InvokeContext context, Object body) {
//
//    }
//
//    @Override
//    public void callWithCallback(InvokeContext context, Object body) {
//
//    }


    @Override
    public void startup() throws LifeCycleException {
        super.startup();
    }

    @Override
    public Connection getConn(String url) throws RemotingException {
        URL u = urlParser.parse(url);
        ConnectionPool pool = connectionManager.getConnectionPoolAndPutIfAbsent(u);
        return pool.get();
    }

    @Deprecated
    private RpcMessageFrame packageRequestMessage(Object message) {
        RpcMessageFrame frame = new RpcMessageFrame();
        frame.setMagic(RpcComstant.MAGIC);
        frame.setProtocol((byte) 0x01);
        frame.setType((byte) 0x01);
        frame.setVersion(RpcProtocolV1.PROTOCOL_VERSION_1);
        frame.setSwitchOption((byte) 0x01);
        frame.setTimeout((short) 30000);
        frame.setId(RpcUtil.UUID());
        frame.setHeader(null);
        frame.setBody(message);
        return frame;
    }

    private RpcMessageFrame packageRequestMessage(Object message, InvokeContext context) {
        RpcMessageFrame frame = new RpcMessageFrame();
        frame.setMagic(RpcComstant.MAGIC);
        frame.setProtocol(context.getProtocolCode());
        frame.setType(context.getType());
        frame.setVersion(context.getVersion());
        byte switchOptions = 0;
        if (context.isCrc()) {
            switchOptions = 0x01 & 0xff;
        }
        frame.setSwitchOption(switchOptions);
        // TODO: 2020/1/11 这个timeout是否有作用？
        frame.setTimeout((short) 0);
        frame.setId(context.getSessionId() != null ? context.getSessionId() : RpcUtil.UUID());
        HashMap<String, Object> header = new HashMap<>();
        header.putAll(context.getProperties());
        frame.setHeader(header);
        frame.setBody(message);
        return frame;
    }

}
