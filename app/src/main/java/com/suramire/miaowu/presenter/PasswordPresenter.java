package com.suramire.miaowu.presenter;

import com.suramire.miaowu.contract.PasswordContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.PasswordModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.subscriptions.CompositeSubscription;

public class PasswordPresenter implements PasswordContract.Presenter {

    private final PasswordModel passwordModel;
    private CompositeSubscription compositeSubscription;
    private PasswordContract.View mView;


    public PasswordPresenter() {
        passwordModel = new PasswordModel();
    }

    @Override
    public void modify() {
        mView.showLoading();
        passwordModel.modify(mView.getUser())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToast("修改密码出错："+e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onSuccess(aVoid);
                    }
                });
    }

    @Override
    public void attachView(PasswordContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
