package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.listener.OnLoginListener;
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

    public void login(){
        mILoginView.showLoading();
        mLoginModel.doLogin(mILoginView.getUserName(), mILoginView.getPassword(), new OnLoginListener() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mILoginView.cancelLoading();
                        mILoginView.onSuccess();
                    }
                });
            }

            @Override
            public void onFailure() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mILoginView.cancelLoading();
                        mILoginView.onFailure();
                    }
                });
            }

            @Override
            public void onError() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mILoginView.cancelLoading();
                        mILoginView.onFailure();
                    }
                });
            }
        });
    }
}
