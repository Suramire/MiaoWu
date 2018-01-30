package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.FansContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.FansModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/1/30.
 */

public class FansPresenter implements FansContract.Presenter {

    private final FansModel fansModel;
    private FansContract.View mView;
    private CompositeSubscription compositeSubscription;

    public FansPresenter() {
        fansModel = new FansModel();
    }

    @Override
    public void attachView(FansContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView =null;
        compositeSubscription.unsubscribe();
    }

    @Override
    public void getFollow() {
        mView.showLoading();
        Subscription subscribe = fansModel.getFollow(mView.getUid())
                .subscribe(new ResponseSubscriber<List<User>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToast("获取关注列表失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        mView.cancelLoading();
                        mView.onSuccess(users);

                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void getFollower() {
        mView.showLoading();
        Subscription subscribe = fansModel.getFollower(mView.getUid())
                .subscribe(new ResponseSubscriber<List<User>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToast("获取粉丝列表失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        mView.cancelLoading();
                        mView.onSuccess(users);

                    }
                });
        compositeSubscription.add(subscribe);

    }
}
