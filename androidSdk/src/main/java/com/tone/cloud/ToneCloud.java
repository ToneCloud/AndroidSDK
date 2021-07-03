package com.tone.cloud;

import android.os.Handler;
import android.util.Log;

import com.tone.cloud.api.mysql.MySQL;
import com.tone.cloud.api.mysql.MySQLImpl;

public class ToneCloud {

    static {
        handler = new Handler();
    }

    private static String publicKey = "";

    private static String privateKey = "";

    private static final Handler handler;

    public static String getPublicKey() {
        return publicKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static Handler getHandler() {
        return handler;
    }

    /**
     * 初始化，在使用 ToneCloud 之前需要现在 Application/Activity 中进行初始化,需要在主线程中初始化
     * @param publicKey 公钥
     * @param privateKey 私钥
     */
    public static void initialize(String publicKey,String privateKey) {
        // handler = new Handler();
        if (publicKey == null || privateKey == null) {
            Log.e("ToneCloud","初始化 key 为空");
            return;
        }
        ToneCloud.publicKey = publicKey;
        ToneCloud.privateKey = privateKey;
    }

    public static MySQL getMysql() {
        return MySQLImpl.getInstance();
    }
}
