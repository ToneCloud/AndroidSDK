package com.tone.cloud.utils;

import com.tone.cloud.utils.encript.MD5Util;
import com.tone.cloud.utils.encript.RC4Util;

public class ToneCloudUtils {

    public static String encryptRC4(String data) {
        return RC4Util.encryptRC4String(data);
    }

    public static String decryptRC4(String data) {
        return RC4Util.decryptRC4(data);
    }

    public static String md5(String data) {
        return MD5Util.md5(data);
    }
}
