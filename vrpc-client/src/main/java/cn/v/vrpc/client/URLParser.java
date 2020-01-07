package cn.v.vrpc.client;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * v
 * 2019/12/16 下午10:11
 * 1.0
 */
public class URLParser {
    private static ConcurrentHashMap<String, URL> caches = new ConcurrentHashMap<>();

    private static final String MARK_COLON = ":";
    private static final String MARK_QUESTION = "?";
    private static final String MARK_EQUAL = "=";
    private static final String MARK_AND = "&";

    /**
     * @param url eg: 127.0.0.1:9527?key1=val1&key2=val2
     * @return
     * @throws Exception
     */
    public URL parse(String url) throws RuntimeException {
        if (caches.contains(url)) {
            return caches.get(url);
        }

        URL u;
        try {
            // filter space
            String urlTmp = url.replaceAll(" ", "");
            int colonIndex = urlTmp.indexOf(MARK_COLON);
            String host = urlTmp.substring(0, colonIndex);

            if (urlTmp.indexOf(MARK_QUESTION) == -1) {
                int port = Integer.valueOf(urlTmp.substring(colonIndex + 1, urlTmp.length()));
                u = new URL(host, port);
                caches.put(url, u);
                return u;
            }
            int questionIndex = urlTmp.indexOf(MARK_QUESTION);
            int port = Integer.valueOf(urlTmp.substring(colonIndex + 1, questionIndex));
            u = new URL(host, port);

            String optionsStr = urlTmp.substring(questionIndex + 1, urlTmp.length());
            String[] properties = optionsStr.split(MARK_AND);
            for (String property : properties) {
                int equalIndex = property.indexOf(MARK_EQUAL);
                if (equalIndex == -1) {
                    throw new URLException("Illegal url, the format of url mull like: [address:host?key1=val1&key2=val2 ...] ");
                }
                u.putOption(property.substring(0, equalIndex), property.substring(equalIndex + 1, property.length()));
            }

        } catch (RuntimeException e) {
            throw new URLException("Resolve url cause a exception: " + e.getMessage());
        }
        caches.put(url, u);
        return u;
    }

    private class URLException extends RuntimeException {

        public URLException() {
        }

        public URLException(String message) {
            super(message);
        }

        public URLException(String message, Throwable cause) {
            super(message, cause);
        }

        public URLException(Throwable cause) {
            super(cause);
        }

        public URLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }


}
