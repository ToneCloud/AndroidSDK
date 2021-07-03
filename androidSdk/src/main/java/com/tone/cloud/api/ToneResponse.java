package com.tone.cloud.api;


/**
 * Tone api 的返回实体类
 */
public class ToneResponse {

    /**
     *  请求结果 code，1为成功，0为失败。
     */
    public final String code;

    /**
     * message 信息，为解密后的明文 json 字符串
     */
    public final String message;

    public ToneResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ToneResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
