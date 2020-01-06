package cn.v.vrpc.client.test;

import cn.v.vrpc.client.ServerConnectionFactory;
import cn.v.vrpc.client.config.ConnectionOptions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;



class ServerConnectionFactoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnectionFactoryTest.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    void start() throws InterruptedException {
        ServerConnectionFactory factory = new ServerConnectionFactory(new ConnectionOptions(),null);
        factory.start();
        countDownLatch.await();
    }
}