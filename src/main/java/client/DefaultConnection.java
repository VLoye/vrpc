/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package client;

import client.core.ClientConfig;

/**
 * @author V
 * @data 2019/9/23 19:30
 * @Description
 **/
public class DefaultConnection extends AbstractConnection {

    public DefaultConnection(String addr, ClientConfig clientConfig, ConnManager manager) {
        super(addr, clientConfig, manager);
    }
}
