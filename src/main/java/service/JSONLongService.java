package service;
/**
 * Created by VLoye on 2019/9/2.
 */

import core.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname JSONLongService
 * @Description
 **/
public class JSONLongService extends AbstractService {
    private static final Logger logger = LoggerFactory.getLogger(JSONLongService.class);

    public JSONLongService() {
    }

    public JSONLongService(ServiceConfig config) {
        super(config);
    }

    @Override
    public void start() {
        super.start();
        logger.info("{} start success.",this.toString());
    }


    //    public static void main(String[] args) {
//        System.out.println(JSONLongService.class.getName());
//    }


    @Override
    public String toString() {
        return "JSONLongService{" + config.getHost() + ":" + config.getPort() + "}";
    }
}
