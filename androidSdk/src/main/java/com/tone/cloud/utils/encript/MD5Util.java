package com.tone.cloud.utils.encript;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {


    public static String md5(String str) {
        if (str == null) return "";
        StringBuilder sb = new StringBuilder();
        byte[] md5Bytes = md5(str.getBytes());
        for (byte b : md5Bytes) {
            String hex = Integer.toHexString( b & 0xff);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    static byte[] md5(byte[] bytes) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        messageDigest.update(bytes);
        return messageDigest.digest();
    }
}
