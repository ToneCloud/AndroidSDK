package com.tone.cloud;

import com.tone.cloud.data.ToneCloudResponse;

public interface ToneCloudCallback {

    void onDone(ToneCloudResponse response);

    void onError(Throwable t);
}
