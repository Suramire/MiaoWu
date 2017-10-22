package com.suramire.miaowu.view;

/**
 * Created by Suramire on 2017/10/21.
 * 登录功能：
 * 登录前 显示进度框
 * 登录中 验证信息
 * 登录后 对应结果
 */

public interface ILoginView {

    void showLoading();

    void cancelLoading();

    String getUserName();

    String getPassword();

    void onSuccess();

    void onFailure();

}
