package com.suramire.miaowu.base;

/**
 * Created by Suramire on 2017/10/25.
 * 通用监听器
 */

public interface OnGetResultListener {
    //成功时 携带结果
    void onSuccess(Object object);
    //失败时 携带失败信息
    void onFailure(String failureMessage);
    //出现错误时 携带错误信息
    void onError(String errorMessage);
}
