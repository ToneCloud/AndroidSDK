package com.tone.cloud;

import android.os.Handler;
import android.os.Looper;

import com.tone.cloud.api.mysql.MySql;

public class ToneCloud {
    static {
        handler = new Handler(Looper.getMainLooper());
    }
    private static final Handler handler;
    private static String privateKey = "";
    private static String publicKey = "";

    public static void initialize(String publicKey,String privateKey) {
        ToneCloud.publicKey = publicKey;
        ToneCloud.privateKey = privateKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static MySql getMySql() {
        return MySql.getInstance();
    }

}
