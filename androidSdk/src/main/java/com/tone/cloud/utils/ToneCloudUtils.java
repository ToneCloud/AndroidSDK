package com.tone.cloud.utils;

import com.tone.cloud.ToneCloud;

import java.io.UnsupportedEncodingException;

/**
 * 工具类
 */
public class ToneCloudUtils {

    /**
     * 计算一个字符串的 32 位小写 md5 值,str 为 null 时返回空字符串
     * @param str 字符串
     * @return 32 位小写 md5 值
     */
    public static String md5(String str) {
        return MD5Util.md5(str);
    }

    public static String md5WithKeys(String str) {
        return MD5Util.md5(ToneCloud.getPublicKey() + str + ToneCloud.getPrivateKey());
    }

    public static String encryRC4String(String data) {
        return RC4Util.encryRC4String(data);
    }

    public static String decryRC4(String data) {
        return RC4Util.decryRC4(data);
    }
}
