package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import org.eclipse.core.runtime.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


class ConnectionFactoryTest {
    String host = "127.0.0.1";
    int port = 9527;

    @Test
    void createConnection() throws Exception {
        URL url = new URL(host, port);
        ConnectionFactory factory = new ConnectionFactory(new ConnectionOptions(), null);
        Connection connection = factory.createConnection(url);
        Assert.isNotNull(connection);
//        Assert.isNotNull(null,"object is null.");
    }
}