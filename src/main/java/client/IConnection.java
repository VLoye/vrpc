/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package client;

import client.core.ClientConfig;
import core.exc.ConnectionException;
import core.message.RpcRequestMessage;

/**
 * @author V
 * @data 2019/9/23 19:01
 * @Description
 **/
public interface IConnection {
    //addr

    boolean isBusy();

    boolean setBusy(boolean busy);

    boolean isActive();

    ResponseFuture call(RpcRequestMessage msg);

    void release();

    void connect() throws ConnectionException;

    void close();

    void reconnect() throws ConnectionException;

    public void start() throws ConnectionException;

    ClientConfig getConfig();

    String getAddress();


}
