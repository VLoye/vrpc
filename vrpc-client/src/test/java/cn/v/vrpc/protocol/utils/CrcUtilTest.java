package cn.v.vrpc.protocol.utils;

import org.eclipse.core.runtime.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrcUtilTest {
    @Test
    public void test() {
        byte[] bytes = "RpcMessageFrame{magic=1, version=1, type=1, switchOption=1, sessionId='8a5b02fe63744354', timeout=30000, protocol=1, codec=0, header=null, body=cn.v.vrpc.client.RpcRequestMessage@3dd4520b, crc=0}".getBytes();
        int crc = CrcUtil.evalCrc32(bytes);
        System.out.println(crc);
        Assert.isTrue(CrcUtil.verCrc32(bytes,crc));
    }
}