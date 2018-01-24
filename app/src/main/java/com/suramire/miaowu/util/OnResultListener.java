package com.suramire.miaowu.util;

/**
 * Created by Suramire on 2018/1/13.
 */

public interface OnResultListener {
    void onError(String errorMessage);

    void onFailed(String failureMessage);

    void onSuccess(Object object);
}
