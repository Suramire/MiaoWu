package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/31.
 */

public interface NotificationContract {

    interface Model<T> {

        Observable<T> getNotifications(int uid);

        Observable<T> readNotification(int notificationId);


    }

    interface View extends BaseView {

        int getUid();

        int getNofiticationId();

        void onReadSuccess(int id);


    }

    interface Presenter extends BasePresenter<View> {

        void getNotifications();

        void readNotification();

    }
}
