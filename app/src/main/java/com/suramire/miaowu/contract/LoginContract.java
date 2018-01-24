package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface LoginContract {
    interface Model<T> {
        Observable<T> doLogin(String username, String password);
    }

    interface View extends BaseView {

        String getUserName();

        String getPassword();

        void onLoginSuccess(Object resultObject);

    }

    interface Presenter extends BasePresenter<View> {
        void login(String name,String password);
    }
}
