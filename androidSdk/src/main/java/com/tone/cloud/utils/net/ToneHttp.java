package com.tone.cloud.utils.net;

import com.tone.cloud.ToneCloud;
import com.tone.cloud.ToneCloudCallback;
import com.tone.cloud.data.ToneCloudResponse;
import com.tone.cloud.utils.ToneCloudUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ToneHttp {

    public static void get(String url, ToneCloudCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockGet(url, callback);
            }
        }).start();
    }

    public static void post(String url, ToneCloudCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockPost(url, callback);
            }
        }).start();
    }

    public static void blockGet(String url, ToneCloudCallback callback) {
        try {
            String result = blockGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String code;
            try {
                code = jsonObject.getInt("code") + "";
            } catch (Exception e) {
                code = jsonObject.getString("code");
            }
            String message = jsonObject.getString("message");
            String finalCode = code;
            message = ToneCloudUtils.decryptRC4(message);
            String finalMessage = message;
            // ToneCloud.getHandler().post( () -> callback.onDone(new ToneResponse(finalCode, finalMessage)));
            ToneCloud.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    callback.onDone(new ToneCloudResponse(finalCode, finalMessage));
                }
            });
        }catch (Throwable t) {
            t.printStackTrace();
            ToneCloud.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(t);
                }
            });
        }
    }

    private static void blockPost(String url, ToneCloudCallback callback) {
        try {
            String result = blockPost(url);
            String de = ToneCloudUtils.decryptRC4(result);
            JSONObject jsonObject = new JSONObject(de);
            String code;
            try {
                code = jsonObject.getInt("code") + "";
            } catch (Exception e) {
                code = jsonObject.getString("code");
            }
            String message = jsonObject.getString("message");

            String finalCode = code;
            ToneCloud.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    callback.onDone(new ToneCloudResponse(finalCode, message));
                }
            });
        }catch (Throwable t) {
            t.printStackTrace();
            ToneCloud.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(t);
                }
            });
        }
    }

    public static String blockGet(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type","application/json");

        connection.setRequestProperty("accept","application/json");
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

    public static String blockPost(String url) throws IOException {
        return blockPost(url,"");
    }

    public static String blockPost(String url,String jsonBody) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("accept","application/json");
        byte[] bytes = jsonBody.getBytes();
        connection.setRequestProperty("Content-Length",bytes.length + "");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();

        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
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
