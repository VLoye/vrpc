package core;
/**
 * Created by VLoye on 2019/8/6.
 */

import core.message.RpcMessage;

/**
 * @author V
 * @Classname IInvocation
 * @Description
 **/
@Deprecated
public interface IInvocation {

    RpcResult invoke(RpcMessage message);

}
