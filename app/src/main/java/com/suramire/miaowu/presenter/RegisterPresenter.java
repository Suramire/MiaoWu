package com.suramire.miaowu.presenter;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.RegisterContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.RegisterModel;
import com.suramire.miaowu.util.ToastUtil;

/**
 * Created by Suramire on 2017/10/22.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterModel mRegisterModel;
    private RegisterContract.View mView;

    public RegisterPresenter() {
        mRegisterModel = new RegisterModel();

    }

    /**
     * 验证手机号是否可以注册
     */
    @Override
    public void validatePhoneNumber(final OnGetResultListener onPhoneValidListener){
        mView.showLoading();
        mRegisterModel.validatePhoneNumber(mView.getPhoneNumber())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onPhoneSuccess();
                    }
                });
    }


    /**
     * 注册验证
     */
    @Override
    public void validateInformation(){
        mView.showLoading();
        mRegisterModel.validateRegisterInformation(mView.getPhoneNumber(), mView.getUserName(), mView.getPassword(), mView.getRePassword())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onSuccess(user);
                    }
                });
    }


    @Override
    public void attachView(RegisterContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
