package client;
/**
 * Created by VLoye on 2019/9/4.
 */

import client.core.IListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @author V
 * @Classname ResponseFuture
 * @Description
 **/
public class ResponseFuture implements Future<Object> {
    private List<IListener> listeners = new ArrayList<IListener>();
    private static final Object SIGNAL_SUCCESS = new Object();
    private static final Object SIGNAL_FAILURE = new Object();

    private Object result;
    private Object signal;


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    public void setSuccess(Object result) {
        this.result = result;
        this.signal = SIGNAL_SUCCESS;
        synchronized (this){
            notifyAll();
            notifyListener();
        }
    }

    public void addListener(IListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        this.listeners.remove(listener);
    }

    private void notifyListener() {
        Iterator<IListener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            IListener listener = iterator.next();
            listener.operationComplete();
        }
    }

    @Override
    @Deprecated
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
//        return get0(timeout,unit);

        if (!await0(timeout)) {
            throw new TimeoutException();
        }
        Object cause = cause();
        if (cause == null) {
            return getNow();
        }
        throw new ExecutionException((Throwable) cause);
    }

    private Object getNow() {
        return this.result;
    }

    private Object cause() {
        if (result instanceof Throwable) {
            return result;
        }
        return null;
    }

    private boolean await0(long timeout) throws InterruptedException {
        if (isDone()) {
            return true;
        }
        if (timeout <= 0) {
            return isDone();
        }
        long startTime = System.nanoTime();


        for (; ; ) {
            try {
                synchronized (this) {
                    wait(timeout);
                }
            } catch (InterruptedException e) {
                throw e;
            }
            if (isDone()) {
                return true;
            } else {
                long duration = System.nanoTime() - startTime;
                if (timeout * 100000 - duration <= 0) {
                    return isDone();
                }
            }
        }

    }


}
