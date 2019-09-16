package core;
/**
 * Created by VLoye on 2019/8/6.
 */

import message.RpcMessage;

/**
 * @author V
 * @Classname IInvocation
 * @Description
 **/
public interface IInvocation {

    RpcResult invoke(RpcMessage message);

}
