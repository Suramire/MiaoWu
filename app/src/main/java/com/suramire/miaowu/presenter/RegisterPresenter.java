package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.listener.OnValidationListener;
import com.suramire.miaowu.model.RegisterModel;
import com.suramire.miaowu.view.IRegisterView;

/**
 * Created by Suramire on 2017/10/22.
 */

public class RegisterPresenter {
    private final RegisterModel mRegisterModel;
    private final Handler mHandler;

    IRegisterView mIRegisterView;

    public RegisterPresenter(IRegisterView IRegisterView) {
        mIRegisterView = IRegisterView;
        mRegisterModel = new RegisterModel();
        mHandler = new Handler();

    }

    /**
     * 验证手机号是否可以注册
     */
    public void validatePhoneNumber(final OnValidationListener onPhoneValidListener){
        mIRegisterView.showLoading();
        mRegisterModel.validatePhoneNumber(mIRegisterView.getPhoneNumber(), new OnValidationListener() {
            @Override
            public void onSuccess() {
                cancelLoading();
                onPhoneValidListener.onSuccess();
            }

            @Override
            public void onFailure(final String failtureMessage) {
                cancelLoading();
                onPhoneValidListener.onFailure(failtureMessage);
            }

            @Override
            public void onError(String errorMessage) {
                cancelLoading();
                onPhoneValidListener.onError(errorMessage);
            }
        });
    }

    private void cancelLoading() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mIRegisterView.cancelLoading();
            }
        });
    }


    /**
     * 注册验证
     */
    public void validateInformation(){
        mIRegisterView.showLoading();
        mRegisterModel.validateRegisterInformation(mIRegisterView.getPhoneNumber(),mIRegisterView.getUserName(), mIRegisterView.getPassword(), mIRegisterView.getRePassword(), new OnValidationListener() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIRegisterView.cancelLoading();
                        mIRegisterView.onRegisterSuccess();

                    }
                });
            }

            @Override
            public void onFailure(final String failtureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIRegisterView.cancelLoading();
                        mIRegisterView.onRegisterFailure(failtureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIRegisterView.cancelLoading();
                        mIRegisterView.onRegisterError(errorMessage);
                    }
                });
            }
        });
    }




}
