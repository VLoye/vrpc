package cn.v.vrpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * v
 * 2019/12/16 下午9:48
 * 1.0
 */
public class ResponseFuture {
    private static final Logger logger = LoggerFactory.getLogger(ResponseFuture.class);

    private static Map<String, ResponseFuture> futures = new ConcurrentHashMap<>();

    private boolean success;
    private String sessionId;
    private long timeout;
    private Object result;
    private Throwable cause;
    private final CountDownLatch latch = new CountDownLatch(1);
    private List<ResponseFutureListener> listeners = new ArrayList<>();

    public ResponseFuture(String sessionId) {
        this.sessionId = sessionId;
    }

    public ResponseFuture(String sessionId, long timeout) {
        this.sessionId = sessionId;
        this.timeout = timeout;
        futures.put(sessionId, this);
    }

    public ResponseFuture valueOf(String id) {
        return futures.remove(id);
    }

    public Object get() throws InterruptedException, RemotingException {
        latch.await();
        if (isSuccess()) {
            return result;
        }
        throw new RemotingException(cause.getMessage());
    }

    public Object get(long timeout) throws InterruptedException, TimeoutException, RemotingException {
        latch.await(timeout, TimeUnit.MILLISECONDS);
        if (!isDone()) {
            TimeoutException e = new TimeoutException();
            fail(e);
            throw e;
        }
        if (isSuccess()) {
            return result;
        }
        throw new RemotingException(cause.getMessage());
    }

    public boolean isDone() {
        return result != null || cause != null;
    }

    public Throwable cause() {
        return cause;
    }

    public String id() {
        return sessionId;
    }

    public boolean isSuccess() {
        if (result != null) {
            return true;
        }
        return false;
    }

    public Object getNow() {
        return result;
    }

    public void fail(Throwable cause) {

        notifyListeners(Evnet.SUCCESS);
        this.cause = cause;
        latch.countDown();
    }

    public void success(Object result) {
        success = true;
        this.result = result;
        // 监听器考虑后台异步的方式执行，防止影响锁释放
        notifyListeners(Evnet.SUCCESS);
        latch.countDown();
    }

    public static ResponseFuture findResponseFuture(String id) {
        return futures.get(id);
    }

    private void notifyListeners(Evnet evnet) {
        switch (evnet) {
            case SUCCESS:
                for (ResponseFutureListener listener : listeners) {
                    try {
                        listener.onSuccess(this);
                    } catch (Throwable e) {
                        logger.error(e.getMessage());
                    }
                }
                break;
            case FAIL:
                for (ResponseFutureListener listener : listeners) {
                    try {
                        listener.onFail(this);
                    } catch (Throwable e) {
                        logger.error(e.getMessage());
                    }
                }
                break;
        }
    }


    enum Evnet {
        SUCCESS,
        FAIL,
        ;
    }


}
