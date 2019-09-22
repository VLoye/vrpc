package core;
/**
 * Created by VLoye on 2019/8/17.
 */

import core.config.ServiceConfig;
import core.message.RpcMessage;

/**
 * @author V
 * @Classname IService
 * @Description
 **/
public interface IService {
    void init();
    void start();
    void close();

    void setConfig(ServiceConfig config);

    void call(RpcMessage message);
}
