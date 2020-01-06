package cn.v.vrpc.client.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    public void test(){
        Option option = new Option();
        option.put("key1","val1");
        option.put("key2","val2");
        option.put("key3","123");
        option.putToInt("int","1");

        String val1 = option.get("key1");
        System.out.println(val1);
        String val2 = option.get("key2");
        System.out.println(val2);

//        int val3 = option.get("key3");
//        System.out.println(val3);

        int val4 = option.get("int");
        System.out.println(val4);


    }

}