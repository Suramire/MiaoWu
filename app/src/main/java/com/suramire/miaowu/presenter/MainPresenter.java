package com.suramire.miaowu.presenter;

import com.suramire.miaowu.contract.MainContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.MainModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/1/31.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainModel mainModel;
    private CompositeSubscription compositeSubscription;
    private MainContract.View mView;

    public MainPresenter() {
        mainModel = new MainModel();

    }



    @Override
    public void getNotificationCount(int uid) {
        Subscription subscribe = mainModel.getNoticationCount(uid)
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToastCenter("获取通知数失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mView.onGetNotificationCountSuccess(integer);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void attachView(MainContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
