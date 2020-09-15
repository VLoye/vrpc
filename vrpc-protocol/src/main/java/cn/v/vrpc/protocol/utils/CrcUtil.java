package cn.v.vrpc.protocol.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.zip.CRC32;

public class CrcUtil {
    private static final Logger logger = LoggerFactory.getLogger(CrcUtil.class);

    private static CRC32 crc32 = new CRC32();

    public static int evalCrc32(byte[] bytes) {
        crc32.update(bytes);
        int value = (int) (crc32.getValue() & 0xffff);
        crc32.reset();
        return value;
    }

    public static boolean verCrc32(byte[] bytes, int crc) {
        return evalCrc32(bytes) == crc;
    }
}
