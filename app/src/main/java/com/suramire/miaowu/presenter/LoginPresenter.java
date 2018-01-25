package com.suramire.miaowu.presenter;

import android.text.TextUtils;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.model.LoginModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginModel mLoginModel;
    private LoginContract.View mView;
    private CompositeSubscription compositeSubscription;

    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String name, String password) {
        mView.showLoading();
        String sName = name;
        String sPassword = password;
        //若传入用户名密码则从页面上的输入框获取
        //两个参数为空表示主页面的自动登录
        if (TextUtils.isEmpty(sName) && TextUtils.isEmpty(sPassword)) {
            sName = mView.getUserName();
            sPassword = mView.getPassword();
        }

        Subscription subscribe = mLoginModel.doLogin(sName, sPassword)
                .subscribe(new Action1<User>() {
                               @Override
                               public void call(User user) {
                                   mView.cancelLoading();
                                   mView.onLoginSuccess(user);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.cancelLoading();
                                   ToastUtil.showShortToastCenter(throwable.getMessage());
                               }
                           }
                );
        compositeSubscription.add(subscribe);

    }

    @Override
    public void attachView(LoginContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
