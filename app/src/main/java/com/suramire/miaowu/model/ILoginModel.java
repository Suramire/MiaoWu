package com.suramire.miaowu.model;

import com.suramire.miaowu.listener.OnLoginListener;

/**
 * Created by Suramire on 2017/10/21.
 */

public interface ILoginModel {
    void doLogin(String username, String password, OnLoginListener onLoginListener);
}
