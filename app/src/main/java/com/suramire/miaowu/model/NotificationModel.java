package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.NotificationContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/31.
 */

public class NotificationModel implements NotificationContract.Model {
    @Override
    public Observable<List<Notification>> getNotifications(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.listNotifications(user)
                .map(new ResponseFunc<List<Notification>>());
    }

    @Override
    public Observable<Integer> readNotification(int notificationId) {
        Notification notification = new Notification();
        notification.setId(notificationId);
        return ApiLoader.readNotification(notification)
                .map(new ResponseFunc<Integer>());
    }
}
