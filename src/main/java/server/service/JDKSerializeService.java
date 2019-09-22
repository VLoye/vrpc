package server.service;
/**
 * Created by VLoye on 2019/9/2.
 */

import core.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname JDKSerializeService
 * @Description
 **/
public class JDKSerializeService extends AbstractService {
    private static final Logger logger = LoggerFactory.getLogger(JDKSerializeService.class);

    public JDKSerializeService() {
    }

    public JDKSerializeService(ServiceConfig config) {
        super(config);
    }

    @Override
    public void start() {
        super.start();
        logger.info("{} start success.",this.toString());
    }


    //    public static void main(String[] args) {
//        System.out.println(JDKSerializeService.class.getName());
//    }


    @Override
    public String toString() {
        return "JDKSerializeService{" + config.getHost() + ":" + config.getPort() + "}";
    }
}
