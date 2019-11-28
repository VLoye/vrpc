package core;/**
 * Created by V on 2019/11/27.
 */

/**
 * V
 * 2019/11/27 17:58
 */
public interface ILifeCycle {

    void init() throws LifeCycleException;
    void start() throws LifeCycleException;
    void close() throws LifeCycleException;
//    void start() throws LifeCycleException;

}
