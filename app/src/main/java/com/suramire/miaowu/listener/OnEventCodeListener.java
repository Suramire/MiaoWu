package com.suramire.miaowu.listener;

/**
 * Created by Suramire on 2017/10/22.
 */

public interface OnEventCodeListener {
    void onEventCode(int code);

    void onEventError(String errorMessage);
}
