package core.message;
/**
 * Created by VLoye on 2019/8/26.
 */

import lombok.Data;

/**
 * @author V
 * @Classname RpcResponseMessage
 * @Description
 **/
@Data
public class RpcResponseMessage extends RpcMessage{
    protected boolean success;
    protected Object result;
    protected Object exc;


}
