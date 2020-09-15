package cn.v.vrpc.client.config;

import io.netty.channel.ChannelOption;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Option {

    public Option() {
        put(NETTY_HIGH_WATER_MARK, 65536);
        put(NETTY_LOW_WATER_MARK, 32768);

        put(NETTY_BOSS_THREADS, NETTY_BOSS_THREADS_DEFAULT);
        put(NETTY_IO_THREADS, NETTY_BOSS_THREADS_DEFAULT);

        put(CONNECTION_POOL_SIZE, CONNECTION_POOL_SIZE_DEFAULT);
        put(CONNECTION_PROTOCOL, CONNECTION_PROTOCOL_DEFAULT);
        put(CONNECTION_IDLE_TIMEOUT, CONNECTION_IDLE_TIMEOUT_DEFAULT);

        put(SERVER_ADDRESS, SERVER_ADDRESS_DEFAULT);
        put(SERVER_PORT, SERVER_PORT_DEFAULT);
    }

    private HashMap<OptionConstant, Object> pool = new HashMap();


    // common
    public static final OptionConstant<Integer> NETTY_HIGH_WATER_MARK = new OptionConstant<Integer>("netty.high.water.mark");
    public static final OptionConstant<Integer> NETTY_LOW_WATER_MARK = new OptionConstant<Integer>("netty.low.water.mark");

    public static final OptionConstant<Integer> NETTY_BOSS_THREADS = new OptionConstant("bossThreads");
    int NETTY_BOSS_THREADS_DEFAULT = 1;

    int NETTY_IO_THREADS_DEFAULT = 0;
    public static final OptionConstant<Integer> NETTY_IO_THREADS = new OptionConstant("ioThreads");


    int CONNECTION_POOL_SIZE_DEFAULT = 1;
    public static final OptionConstant<Integer> CONNECTION_POOL_SIZE = new OptionConstant("poolSize");

    String CONNECTION_PROTOCOL_DEFAULT = "rpc";
    public static final OptionConstant<String> CONNECTION_PROTOCOL = new OptionConstant("protocol");


    long CONNECTION_IDLE_TIMEOUT_DEFAULT = 60 * 1000;
    public static final OptionConstant<Long> CONNECTION_IDLE_TIMEOUT = new OptionConstant("idleTimeout");

    // client
    // server


    int SERVER_PORT_DEFAULT = 9527;
    public static final OptionConstant<Integer> SERVER_PORT = new OptionConstant("port");

    String SERVER_ADDRESS_DEFAULT = "127.0.0.1";
    public static final OptionConstant<String> SERVER_ADDRESS = new OptionConstant("address");


    public <T> void put(OptionConstant<T> constant, T val) {
        pool.put(constant, val);
    }

    public <T> T get(OptionConstant<T> constant) {
        return (T) pool.get(constant);
    }

    static class OptionConstant<T> {
        String id;
        T value;

        public OptionConstant(String id) {
            this.id = id;
        }

        T value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OptionConstant<?> that = (OptionConstant<?>) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}


