package cn.v.vrpc.protocol.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

public class CloseableUtil {
    public static void safeRelease(Closeable... closeables){
        Arrays.stream(closeables).forEach(x->{
            if(x!=null){
                try {
                    x.close();
                } catch (IOException e) {
                    //nothing to do
                }
            }
        });
    }

}
