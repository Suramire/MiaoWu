package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.contract.NotificationContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.NotificationModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/1/31.
 */

public class NotificationPresenter implements NotificationContract.Presenter {
    private final NotificationModel notificationModel;
    private NotificationContract.View mView;
    private CompositeSubscription compositeSubscription;

    public NotificationPresenter() {
        notificationModel = new NotificationModel();
    }


    @Override
    public void attachView(NotificationContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }

    @Override
    public void getNotifications() {
        mView.showLoading();
        Subscription subscribe = notificationModel.getNotifications(mView.getUid())
                .subscribe(new ResponseSubscriber<List<Notification>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("获取通知列表失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Notification> notifications) {
                        mView.cancelLoading();
                        mView.onSuccess(notifications);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void readNotification() {
        Subscription subscribe = notificationModel.readNotification(mView.getNofiticationId())
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLongToastCenter("读取通知信息失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer o) {
                        mView.onReadSuccess(o);
                    }
                });
        compositeSubscription.add(subscribe);

    }
}
