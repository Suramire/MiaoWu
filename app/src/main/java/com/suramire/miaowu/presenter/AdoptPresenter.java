package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.AdoptContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.AdoptModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/4/9.
 */

public class AdoptPresenter implements AdoptContract.Presenter {

    private final AdoptModel adoptModel;
    private AdoptContract.View mView;
    private CompositeSubscription compositeSubscription;


    public AdoptPresenter() {
        adoptModel = new AdoptModel();
    }

    @Override
    public void attachView(AdoptContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }

    @Override
    public void getAdoptHistory() {
        mView.showLoading();
        Subscription subscribe = adoptModel.getAdoptHistory(mView.getUid())
                .subscribe(new ResponseSubscriber<List<Catinfo>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("获取领养记录出错:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Catinfo> applies) {
                        mView.cancelLoading();
                        mView.onSuccess(applies);
                    }
                });
        compositeSubscription.add(subscribe);

    }
}
