package com.suramire.miaowu.listener;

/**
 * Created by Suramire on 2017/10/22.
 */

public interface OnValidationListener {
    void onSuccess();

    void onFailure(String failtureMessage);

    void onError(String errorMessage);


}
