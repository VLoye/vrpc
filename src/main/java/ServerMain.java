/**
 * Created by VLoye on 2019/9/3.
 */

import client.AService;
import core.config.HearBeatConfig;
import core.config.RpcConfig;
import core.config.ServiceConfig;
import core.exc.RpcException;
import server.RpcServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author V
 * @Classname ServerMain
 * @Description
 **/
public class ServerMain {
    public static final String SERVICE_CLASS_NAME = "server.service.JDKSerializeService";


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        HearBeatConfig hearBeatConfig = new HearBeatConfig();
        hearBeatConfig.setUseful(true);

        RpcConfig config = new RpcConfig();
        List<ServiceConfig> serviceConfigs = new ArrayList<ServiceConfig>();
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setServiceClassName(SERVICE_CLASS_NAME);
        serviceConfigs.add(serviceConfig);
        serviceConfig.setHearBeatConfig(hearBeatConfig);
        config.setConfigs(serviceConfigs);

        try {
            RpcServer server = new RpcServer(config);
            server.start();
            server.registerInterface(AService.class);
        } catch (RpcException e) {
            e.printStackTrace();
        }

        latch.await();
    }
}
