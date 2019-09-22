package core;
/**
 * Created by VLoye on 2019/8/17.
 */

/**
 * @author V
 * @Classname InvocationFactory
 * @Description
 **/
@Deprecated
public class InvocationFactory {

    public static IInvocation getInvocation(){
        return new DefaultInvocation();
    }

}
