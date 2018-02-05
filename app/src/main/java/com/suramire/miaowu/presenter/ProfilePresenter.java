package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ProfileModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/25.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileModel mProfileModel;
    private ProfileContract.View mView;
    private CompositeSubscription compositeSubscription;

    public ProfilePresenter() {
        mProfileModel = new ProfileModel();
    }

    @Override
    public void getProfile(){
        mView.showLoading();
        Subscription subscribe = mProfileModel.getProfile(mView.getUid())
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
    public void updateProfile() {
        mView.showLoading();
        Subscription subscribe = mProfileModel.updateProfile(mView.getUser())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("修改用户信息失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onUpdateSuccess(user);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void attachView(ProfileContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }
}
