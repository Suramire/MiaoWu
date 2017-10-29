package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface LoginContract {
    interface Model {
        void doLogin(String username, String password, OnGetResultListener listener);
    }

    interface View {
        void showLoading();

        void cancelLoading();

        String getUserName();

        String getPassword();

        void onLoginSuccess(Object resultObject);

        void onLoginFailure(String fialureMessage);

        void onLoginError(String errorMessage);
    }

    interface Presenter {
        void login(String name,String password);
    }
}
