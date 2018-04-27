package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ReviewApplyContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ReviewApplyModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ReviewApplyPresenter implements ReviewApplyContract.Presenter {

    private final ReviewApplyModel reviewApplyModel;
    private CompositeSubscription compositeSubscription;
    private ReviewApplyContract.View mView;


    public ReviewApplyPresenter() {
        reviewApplyModel = new ReviewApplyModel();
    }

    @Override
    public void reviewApply() {
        mView.showLoading();
        Subscription subscribe = reviewApplyModel.reviewApply(mView.getCid(), mView.getFlag())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("审核过程出错：" + e.getMessage());
                    }

                    @Override
                    public void onNext(Void o) {
                        mView.cancelLoading();
                        mView.onSuccess(null);
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void getUserInfo() {
        mView.showLoading();
        Subscription subscribe = reviewApplyModel.getUserInfo(mView.getUid())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("获取作者信息失败:" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onGetUserSuccess(user);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getCatInfo() {
        mView.showLoading();
        Subscription subscribe = reviewApplyModel.getCatInfo(mView.getCid())
                .subscribe(new ResponseSubscriber<Catinfo>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("获取猫咪信息失败:"+throwable.getMessage());
                    }

                    @Override
                    public void onNext(Catinfo catinfo) {
                        mView.cancelLoading();
                        mView.onGetCatSuccess(catinfo);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void attachView(ReviewApplyContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
