package core;
/**
 * Created by VLoye on 2019/8/6.
 */


import core.message.RpcMessage;

/**
 * @author V
 * @Classname AbstractInvocation
 * @Description
 **/
@Deprecated
public abstract class AbstractInvocation implements IInvocation {

    @Override
    public RpcResult invoke(RpcMessage message) {
//        return ServiceFactory.getService(core.message.get).call(core.message);
        return null;
    }

}
