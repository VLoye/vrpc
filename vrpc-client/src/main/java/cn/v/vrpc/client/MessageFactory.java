package cn.v.vrpc.client;

/**
 * v
 * 2019/12/28 下午1:20
 * 1.0
 */
public interface MessageFactory<T> {
    T request();
    T response();
    T ping();
    T pong();

}
