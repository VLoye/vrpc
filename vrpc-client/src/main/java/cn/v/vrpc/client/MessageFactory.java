package cn.v.vrpc.client;

import cn.v.vrpc.client.refactoring.Refactoringable;

/**
 * v
 * 2019/12/28 下午1:20
 * 1.0
 */
public interface MessageFactory<T> {
    T request();

    T response();

    @Refactoringable
    T ping();

    @Refactoringable
    T pong();

}
