package client.core;
/**
 * Created by VLoye on 2019/9/4.
 */

import client.ResponseFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname IListener
 * @Description
 **/
public interface IListener {


    void operationComplete(ResponseFuture future);

    IListener completeListener = new IListener() {
        private final Logger logger = LoggerFactory.getLogger(completeListener.getClass());

        @Override
        public void operationComplete(ResponseFuture future) {
            logger.debug("Future[sessionId={}] success.", future.getSessionId());
        }
    };

}
