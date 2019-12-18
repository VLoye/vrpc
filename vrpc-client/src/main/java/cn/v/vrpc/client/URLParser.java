package cn.v.vrpc.client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * v
 * 2019/12/16 下午10:11
 * 1.0
 */
public class URLParser {
    private static final String MARK_COLON = ":";
    private static final String MARK_QUESTION = "?";
    private static final String MARK_EQUAL = "=";
    private static final String MARK_AND = "&";
    private ConcurrentHashMap<String, URL> caches = new ConcurrentHashMap<>();

    public URL parse(String address) throws Exception{
        // filter space
        String addr = address.replaceAll(" ","");
        int colonIndex = addr.indexOf(MARK_COLON);
        String host = addr.substring(0,colonIndex);

        int questionIndex = addr.indexOf(MARK_QUESTION);
        int port = Integer.valueOf(addr.substring(colonIndex+1,questionIndex));

        return new URL(host,port);
    }

}
