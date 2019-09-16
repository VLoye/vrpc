package client;
/**
 * Created by VLoye on 2019/9/4.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname IListener
 * @Description
 **/
public interface IListener {


    void operationComplete();

    IListener completeListener = new IListener() {
        private final Logger logger = LoggerFactory.getLogger(completeListener.getClass());
        @Override
        public void operationComplete() {
            logger.info("listener notify success");
        }
    };

}
