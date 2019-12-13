package cn.v.vrpc.protocol.utils;

import sun.misc.CRC16;

import java.util.zip.CRC32;

//todo

public class CrcUtil {

    private static CRC32 crc32 = new CRC32();

    public static int evalCrc32(byte[] bytes) {
        crc32.update(bytes);
        int value = (int) (crc32.getValue() & 0xffffL);
        crc32.reset();
        return value;
    }

    public static boolean verCrc32(byte[] bytes, int crc) {
        return evalCrc32(bytes) == crc;
    }
}
