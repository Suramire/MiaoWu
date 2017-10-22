package com.suramire.miaowu.presenter;

import android.os.Handler;
import android.widget.Toast;

import com.suramire.miaowu.base.App;
import com.suramire.miaowu.listener.OnEventCodeListener;
import com.suramire.miaowu.listener.OnValidationListener;
import com.suramire.miaowu.model.RegisterModel;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.view.IRegisterView;

import cn.smssdk.SMSSDK;

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

    public void validatePhoneNumber(){
//        mIRegisterView.showLoading();
        mRegisterModel.validatePhoneNumber(mIRegisterView.getPhoneNumber(), new OnValidationListener() {
            @Override
            public void onSuccess() {
                //手机号可以进行注册
                //判断手机号能否接收验证码
                mRegisterModel.registerEventHandler(new OnEventCodeListener() {
                    @Override
                    public void onEventCode(int code) {
                        if(code == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
//                                    mIRegisterView.cancelLoading();
                                    mIRegisterView.goStep1();
                                    L.e("手机可以注册");
                                }
                            });

                        }
                    }

                    @Override
                    public void onEventError(final String errorMessage) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
//                                mIRegisterView.cancelLoading();
                                Toast.makeText(App.getApp(), "手机号码验证失败："+errorMessage, Toast.LENGTH_SHORT).show();
                                L.e("手机不能接收验证码 出现异常"+errorMessage);

                            }
                        });
                    }
                });


            }

            @Override
            public void onFailure(final String failtureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        mIRegisterView.cancelLoading();
                        Toast.makeText(App.getApp(), "手机号码验证失败："+failtureMessage, Toast.LENGTH_SHORT).show();
                        L.e("手机号已被注册");
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public void validateValidationNumber(){
//        mIRegisterView.showLoading();
        mRegisterModel.registerEventHandler(new OnEventCodeListener() {
            @Override
            public void onEventCode(int code) {
                if(code ==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
//                            mIRegisterView.cancelLoading();
                            mIRegisterView.goStep2();
                            L.e("验证码验证成功");
                        }
                    });
                }
            }

            @Override
            public void onEventError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        mIRegisterView.cancelLoading();
                        Toast.makeText(App.getApp(), "验证码验证失败："+errorMessage, Toast.LENGTH_SHORT).show();
                        L.e("验证码验证失败 出现异常"+errorMessage);
                    }
                });
            }
        });

        mRegisterModel.validateValidationNumber(mIRegisterView.getValidationNumber(), new OnValidationListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(final String failtureMessage) {}

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    /**
     * 注册验证
     */
    public void validateInformation(){
//        mIRegisterView.showLoading();
        mRegisterModel.validateRegisterInformation(mIRegisterView.getPhoneNumber(),mIRegisterView.getUserName(), mIRegisterView.getPassword(), mIRegisterView.getRePassword(), new OnValidationListener() {
            @Override
            public void onSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        mIRegisterView.cancelLoading();
                        mIRegisterView.onRegisterSuccess();
//                        mRegisterModel.unregisterEventHandler();

                    }
                });
            }

            @Override
            public void onFailure(final String failtureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        mIRegisterView.cancelLoading();
                        mIRegisterView.onRegisterFailure(failtureMessage);
//                        mRegisterModel.unregisterEventHandler();
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        mIRegisterView.cancelLoading();
                        mIRegisterView.onRegisterError(errorMessage);
//                        mRegisterModel.unregisterEventHandler();
                    }
                });
            }
        });
    }




}
