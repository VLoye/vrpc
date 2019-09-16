package core.config;
/**
 * Created by VLoye on 2019/8/8.
 */

import lombok.Data;

import java.util.List;

/**
 * @author V
 * @Classname RpcConfig
 * @Description
 **/
@Data
public class RpcConfig {
    private List<ServiceConfig> configs;
}
