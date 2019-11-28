package client;

import core.config.HearBeatConfig;
import lombok.Data;

/**
 * @author V
 * @data 2019/9/24 19:26
 * @Description
 **/
@Data
public class ConnectionConfig {
    private long idleTimeout;
    private HearBeatConfig hearBeatConfig;

    public ConnectionConfig() {
    }

    public ConnectionConfig(long idleTimeout, HearBeatConfig hearBeatConfig) {
        this.idleTimeout = idleTimeout;
        this.hearBeatConfig = hearBeatConfig;
    }

    public static ConnectionConfig.Build custom(){
        return new Build();
    }

    public static class Build {
        private long idleTimeout;
        private HearBeatConfig hearBeatConfig;


        public Build() {
            this.idleTimeout = 6000;
            this.hearBeatConfig = new HearBeatConfig();
        }

        public ConnectionConfig build() {
            return new ConnectionConfig(idleTimeout, hearBeatConfig);
        }
    }

}
