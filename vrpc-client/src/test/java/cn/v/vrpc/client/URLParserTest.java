package cn.v.vrpc.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class URLParserTest {

    @Test
    void parse() {
        String url = "127.0.0.1:9527?ke1=val2&key2=val2";
        URLParser urlParser = new URLParser();
        URL u = urlParser.parse(url);
        System.out.println(u.toString());
    }
}