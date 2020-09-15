package cn.v.vrpc.client;

/**
 * v
 * 2019/12/29 上午12:14
 * 1.0
 */
public class VrpcClientAdapter extends AbstractClient {

    public VrpcClientAdapter() {
    }

    public VrpcClientAdapter(URLParser urlParser, ConnectionManager connectionManager) {
        super(urlParser, connectionManager);
    }
}
