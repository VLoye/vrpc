package cn.v.vrpc.client;
/**
 * Created by VLoye on 2019/8/17.
 */

import java.util.UUID;

/**
 * @author V
 * @Classname RpcUtil
 * @Description
 **/
public class RpcUtil {
    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,16);
    }

}
