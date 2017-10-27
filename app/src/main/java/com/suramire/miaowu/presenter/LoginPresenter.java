package com.suramire.miaowu.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.model.ILoginModel;
import com.suramire.miaowu.model.LoginModel;
import com.suramire.miaowu.view.ILoginView;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginPresenter {
    private final ILoginModel mLoginModel;
    private final Handler mHandler;
    private ILoginView mILoginView;

    public LoginPresenter(ILoginView ILoginView) {
        mILoginView = ILoginView;
        mLoginModel = new LoginModel();
        mHandler = new Handler();
    }

    public void login(String name,String password){
        mILoginView.showLoading();
        String sName = name;
        String sPassword = password;
        //若传入用户名密码则从页面上的输入框获取
        //两个参数为空表示主页面的自动登录
        if(TextUtils.isEmpty(sName) && TextUtils.isEmpty(sPassword)){
            sName = mILoginView.getUserName();
            sPassword = mILoginView.getPassword();
        }
        mLoginModel.doLogin(sName, sPassword, new OnGetResultListener() {
            @Override
            public void onSuccess(final Object resultString) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mILoginView.cancelLoading();
                        mILoginView.onLoginSuccess(resultString);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mILoginView.cancelLoading();
                        mILoginView.onLoginFailure(failureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mILoginView.cancelLoading();
                        mILoginView.onLoginError(errorMessage);
                    }
                });
            }
        });
    }
}
