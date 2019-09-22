package core.config;
/**
 * Created by VLoye on 2019/8/17.
 */

import lombok.Data;

/**
 * @author V
 * @Classname ServiceConfig
 * @Description
 **/
@Data
public class ServiceConfig {
    private String serviceClassName;
    private String host = "127.0.0.1";
    private int port=9999;
    private int ioThreads=2;
    private int workThreads=2;
    private long idleTimeOut = 30000;
    private long sessionTimeOut = 30000;
    private String charset = "UTF-8";
    private HearBeatConfig hearBeatConfig;

}
