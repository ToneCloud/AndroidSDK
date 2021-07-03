package com.tone.cloud;

import com.tone.cloud.api.ToneResponse;

/**
 * use ToneCloudCallback instead
 */
@Deprecated
public interface ToneCloudListener {

    void onDone(ToneResponse response);

    void onError(ToneResponse response);
}
