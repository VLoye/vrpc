package client.core;
/**
 * Created by V on 2019/9/22.
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//client:
//    manager:
//        conns:
//        strategy:
//    conn:
//        heartbeat:
//            use:
//            msg:
//            ack:
//        idletimeout:
//    String interfaces;

/**
 * @author V
 * @Classname ClientConfig
 * @Description
 **/
@Data
public class ClientConfig {
    private List<String> addrs = new ArrayList<String>();
    /**
     * road balance strategy
     * 1¡¢round-robin
     */
    private int strategy;
//    private int retries;
//    private boolean useRetry;

    //The time of idle timeout.
    private long idleTimeout;


    // whether to  use heartbeat
    private boolean useHeartbeat;
    //heartbeat message
    private byte[] msg;
    private byte[] ack;

    public ClientConfig() {
    }

    public ClientConfig(List<String> addrs, int strategy, long idleTimeout, boolean useHeartbeat, byte[] msg, byte[] ack) {
        this.addrs = addrs;
        this.strategy = strategy;
        this.idleTimeout = idleTimeout;
        this.useHeartbeat = useHeartbeat;
        this.msg = msg;
        this.ack = ack;
    }

    public static ClientConfig.Builder custom() {
        return new Builder();
    }

    public static class Builder {
        private List<String> addrs = new ArrayList<String>();
        private int strategy;
        private long idleTimeout;
        private boolean useHeartbeat;
        private byte[] msg;
        private byte[] ack;

        public Builder() {
            this.addrs = Arrays.stream(new String[]{"127.0.0.1:9999"}).collect(Collectors.toList());
            this.strategy = 1;
            this.idleTimeout = 30000;
            this.useHeartbeat = false;
            this.msg = "msg".getBytes();;
            this.ack = "ack".getBytes();
        }

        public List<String> getAddrs() {
            return addrs;
        }

        public void setAddrs(List<String> addrs) {
            this.addrs = addrs;
        }

        public int getStrategy() {
            return strategy;
        }

        public void setStrategy(int strategy) {
            this.strategy = strategy;
        }

        public long getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(long idleTimeout) {
            this.idleTimeout = idleTimeout;
        }

        public boolean isUseHeartbeat() {
            return useHeartbeat;
        }

        public void setUseHeartbeat(boolean useHeartbeat) {
            this.useHeartbeat = useHeartbeat;
        }

        public byte[] getMsg() {
            return msg;
        }

        public void setMsg(byte[] msg) {
            this.msg = msg;
        }

        public byte[] getAck() {
            return ack;
        }

        public void setAck(byte[] ack) {
            this.ack = ack;
        }
        public ClientConfig build(){
            return new ClientConfig(addrs,strategy,idleTimeout, useHeartbeat,msg,ack);
        }
    }
}
