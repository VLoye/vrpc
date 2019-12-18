package cn.v.vrpc.client.config;

import java.util.Objects;

@Deprecated
public class Option<T> {
    private T value;

    public Option(T value) {
        this.value = value;
    }

    public T value(){
        return value;
    }
}
