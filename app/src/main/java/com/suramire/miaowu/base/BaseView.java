package com.suramire.miaowu.base;

/**
 * 视图基类
 */

public interface BaseView<T> {
    //显示加载框
    void showLoading();
    //取消加载框
    void cancelLoading();
    //操作成功时
    void onSuccess(T data);
}
