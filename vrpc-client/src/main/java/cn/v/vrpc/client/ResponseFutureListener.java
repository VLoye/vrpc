package cn.v.vrpc.client;

/**
 * v
 * 2020/1/9 上午12:00
 * 1.0
 */
public interface ResponseFutureListener {
    public void onSuccess(ResponseFuture future) throws Throwable;

    public void onFail(ResponseFuture future) throws Throwable;

}
