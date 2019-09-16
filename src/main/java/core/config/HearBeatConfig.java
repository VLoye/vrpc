package core.config;
/**
 * Created by VLoye on 2019/9/2.
 */

import lombok.Data;

/**
 * @author V
 * @Classname HearBeatConfig
 * @Description
 **/
@Data
public class HearBeatConfig {
    private boolean useful = false;
    //心跳报文
    private Object msg = "msg".getBytes();
    private Object ack = "ack".getBytes();
}
