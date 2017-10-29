package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.model.RegisterModel;
import com.suramire.miaowu.view.IRegisterView;

/**
 * Created by Suramire on 2017/10/22.
 */

public class RegisterPresenter {
    private final RegisterModel mRegisterModel;
    private final Handler mHandler;
    private final IRegisterView mIRegisterView;

    public RegisterPresenter(IRegisterView IRegisterView) {
        mIRegisterView = IRegisterView;
        mRegisterModel = new RegisterModel();
        mHandler = new Handler();

    }

    /**
     * 验证手机号是否可以注册
     */
    public void validatePhoneNumber(final OnGetResultListener onPhoneValidListener){
        mIRegisterView.showLoading();
        mRegisterModel.validatePhoneNumber(mIRegisterView.getPhoneNumber(), new OnGetResultListener() {
            @Override
            public void onSuccess(Object object) {
                cancelLoading();
                onPhoneValidListener.onSuccess(null);
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
        mRegisterModel.validateRegisterInformation(mIRegisterView.getPhoneNumber(),mIRegisterView.getUserName(), mIRegisterView.getPassword(), mIRegisterView.getRePassword(), new OnGetResultListener() {
            @Override
            public void onSuccess(Object object) {
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
