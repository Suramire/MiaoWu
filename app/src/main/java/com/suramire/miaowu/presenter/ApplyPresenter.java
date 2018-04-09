package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.contract.ApplyContract;
import com.suramire.miaowu.contract.FansContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ApplyModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/4/9.
 */

public class ApplyPresenter implements ApplyContract.Presenter {

    private final ApplyModel applyModel;
    private ApplyContract.View mView;
    private CompositeSubscription compositeSubscription;


    public ApplyPresenter() {
        applyModel = new ApplyModel();
    }

    @Override
    public void attachView(ApplyContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }

    @Override
    public void getApplys() {
        mView.showLoading();
        Subscription subscribe = applyModel.getApplys(mView.getUid())
                .subscribe(new ResponseSubscriber<List<Apply>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取领养记录出错:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Apply> applies) {
                        mView.cancelLoading();
                        mView.onSuccess(applies);
                    }
                });
        compositeSubscription.add(subscribe);

    }
}
