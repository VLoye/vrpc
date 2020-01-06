package cn.v.vrpc.client;

import java.util.concurrent.TimeoutException;

/**
 * v
 * 2019/12/16 下午9:51
 * 1.0
 */
public interface Connection {

    Object syncInvoke(Object msg) throws RemotingException;
    Object syncInvoke(Object msg,long timeout) throws RemotingException, TimeoutException;

    Object asyncInvoke(Object msg) throws RemotingException;

}
