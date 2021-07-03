package com.tone.cloud.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tone.cloud.ToneCloud;
import com.tone.cloud.ToneCloudCallback;
import com.tone.cloud.ToneCloudListener;
import com.tone.cloud.api.ToneResponse;
import com.tone.cloud.utils.RC4Util;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class ToneHttpClient {

    @Deprecated
    public static void get(String url, ToneCloudListener listener) {
        if (Build.VERSION.SDK_INT >= 24) {
            getH(url, listener);
        } else {
            getL(url, listener);
        }
    }

    public static void get(String url, ToneCloudCallback callback) {
        if (Build.VERSION.SDK_INT >= 24) {
            CompletableFuture.runAsync(() -> toneGet(url, callback));
        } else {
            new Thread(() -> toneGet(url, callback)).start();
        }
    }

    private static void toneGet(String url, ToneCloudCallback callback) {
        try {
            String s = get(url);
            JSONObject jsonObject = new JSONObject(s);
            String code;
            try {
                code = jsonObject.getInt("code") + "";
            } catch (Exception e) {
                code = jsonObject.getString("code");
            }
            String message = jsonObject.getString("message");
            String finalCode = code;
            message = RC4Util.decryRC4(message);
            String finalMessage = message;
            ToneCloud.getHandler().post( () -> callback.onDone(new ToneResponse(finalCode, finalMessage)));
        } catch (Exception e) {
            e.printStackTrace();
            ToneCloud.getHandler().post( () -> callback.onError(e));
        }
    }


    /**
     * 安卓7以上的 get
     * @param url get 请求地址
     * @param listener 回调
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void getH(String url, ToneCloudListener listener) {
        CompletableFuture.runAsync(() -> {
            try {
                String s = get(url);
                JSONObject jsonObject = new JSONObject(s);
                String code;
                try {
                    code = jsonObject.getInt("code") + "";
                } catch (Exception e) {
                    code = jsonObject.getString("code");
                }
                String message = jsonObject.getString("message");
                String finalCode = code;
                message = RC4Util.decryRC4(message);
                String finalMessage = message;
                ToneCloud.getHandler().post( () -> listener.onDone(new ToneResponse(finalCode, finalMessage)));
            } catch (Exception e) {
                e.printStackTrace();
                ToneCloud.getHandler().post( () -> listener.onError(new ToneResponse("error", e.getMessage())));
            }
        });

    }

    /**
     * 安卓7之前的 get
     * @param url get请求地址
     * @param listener 回调
     */
    private static void getL(String url,ToneCloudListener listener) {
        new Thread(() -> {
            try {
                String s = get(url);
                JSONObject jsonObject = new JSONObject(s);
                String code;
                try {
                    code = jsonObject.getInt("code") + "";
                } catch (Exception e) {
                    code = jsonObject.getString("code");
                }
                String message = jsonObject.getString("message");
                String finalCode = code;
                message = RC4Util.decryRC4(message);
                String finalMessage = message;
                ToneCloud.getHandler().post( () -> listener.onDone(new ToneResponse(finalCode, finalMessage)));
            } catch (Exception e) {
                e.printStackTrace();
                ToneCloud.getHandler().post( () -> listener.onError(new ToneResponse("error", e.getMessage())));
            }
        }).start();
    }

    private static String get(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type","application/json");
        InputStream inputStream = connection.getInputStream();
        StringBuilder sb = new StringBuilder();
        try {
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                sb.append(new String(buf,0,len));
            }
        } finally {
            inputStream.close();
            connection.disconnect();
        }
        return sb.toString();
    }
}
