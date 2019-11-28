/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package client;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author V
 * @data 2019/9/23 19:01
 * @Description
 **/
@Data
public class ConnManagerConfig {


    private List<String> addrs = new ArrayList<String>();
    /**
     * 负载均衡策略
     * 1、轮询
     */
    private int strategy;

    public ConnManagerConfig() {
    }


    public static ConnManagerConfig.Builder custom(){
        return new Builder();
    }

    public ConnManagerConfig(List<String> addrs, int strategy) {
        this.addrs = addrs;
        this.strategy = strategy;
    }

    public static class Builder{
        private List<String> addrs = new ArrayList<String>();
        private int strategy;

        public Builder() {
            this.addrs = Arrays.stream(new String[]{"127.0.0.1"}).collect(Collectors.toList());
            this.strategy = 1;
        }


        public ConnManagerConfig build(){
            return new ConnManagerConfig(addrs,strategy);
        }

    }

}
