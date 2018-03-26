package com.suramire.miaowu.presenter;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.RegisterContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.RegisterModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/22.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterModel mRegisterModel;
    private RegisterContract.View mView;
    private CompositeSubscription compositeSubscription;

    public RegisterPresenter() {
        mRegisterModel = new RegisterModel();

    }

    /**
     * 验证手机号是否可以注册
     */
    @Override
    public void validatePhoneNumber(){
        mView.showLoading();
        Subscription subscribe = mRegisterModel.validatePhoneNumber(mView.getPhoneNumber())
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
        compositeSubscription.add(subscribe);

    }


    /**
     * 注册验证
     */
    @Override
    public void validateInformation(){
        mView.showLoading();
        Subscription subscribe = mRegisterModel.validateRegisterInformation(mView.getPhoneNumber(), mView.getUserName(), mView.getPassword(), mView.getRePassword())
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
        compositeSubscription.add(subscribe);

    }


    @Override
    public void attachView(RegisterContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }
}
