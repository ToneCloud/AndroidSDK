package com.tone.cloud;

import com.tone.cloud.api.ToneResponse;

public interface ToneCloudCallback {

    void onDone(ToneResponse response);

    void onError(Exception e);
}
