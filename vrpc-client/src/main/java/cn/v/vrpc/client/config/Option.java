package cn.v.vrpc.client.config;

import java.util.HashMap;
import java.util.Objects;



@Deprecated
public class Option {
    private HashMap<String, Object> options = new HashMap<>();

//    public <T> T get(String key){
//        return (T)options.get(key);
//    }

    public Object get(String key) {
        return options.get(key);
    }
//
//    public int getInt(String key){
//        return Integer.valueOf()
//    }

    public String getString(String key) {
        return String.valueOf(get(key));
    }

    public void put(String key, String value) {
        options.put(key, value);
    }

    public void putToInt(String key, String value) {
        Integer val = Integer.valueOf(value);
        options.put(key, val);
    }

    public void putToLong(String key, String value) {
        Long val = Long.valueOf(value);
        options.put(key, val);
    }
//    public <T> T getByType(String key,OptionType type){
//        Object val = get(key);
//        switch (type){
//            case TYPE_INt:
//                if(val instanceof String)
//                    return (T)Integer.valueOf(get);
//                else {
//                    return (T)String.valueOf(val);
//                }
//            case TYPE_LONG:
//
//        }


    enum OptionType {
        TYPE_INt,
        TYPE_LONG,
        TYPE_STRING,
        ;
    }
}


