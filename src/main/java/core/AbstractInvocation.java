package core;
/**
 * Created by VLoye on 2019/8/6.
 */


import message.RpcMessage;

/**
 * @author V
 * @Classname AbstractInvocation
 * @Description
 **/
public abstract class AbstractInvocation implements IInvocation {

    @Override
    public RpcResult invoke(RpcMessage message) {
//        return ServiceFactory.getService(message.get).call(message);
        return null;
    }

}
