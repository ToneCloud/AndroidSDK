package com.tone.cloud.data;

public class ToneCloudResponse {

    public final String code;

    public final String message;

    public ToneCloudResponse(String code,String message) {
        this.code = code;
        this.message = message;
    }
}
