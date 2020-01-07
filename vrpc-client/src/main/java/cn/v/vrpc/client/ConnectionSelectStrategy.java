package cn.v.vrpc.client;

import java.util.List;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public interface ConnectionSelectStrategy {

    Connection select(List<Connection> connections);
}
