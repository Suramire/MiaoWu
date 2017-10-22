package com.suramire.miaowu.listener;

/**
 * Created by Suramire on 2017/10/21.
 */

public interface OnLoginListener {
    void onSuccess(String resultJson);

    void onFailure(String FailureMessage);

    void onError(String errorMessage);
}
