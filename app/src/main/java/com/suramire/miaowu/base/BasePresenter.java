package com.suramire.miaowu.base;

/**
 * 控制基类
 */

public interface BasePresenter<T extends BaseView> {
    //绑定视图
    void attachView(T view);
    //解绑视图
    void detachView();
}
