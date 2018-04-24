package com.suramire.miaowu.http.base;

import rx.Subscriber;

/**
 * Created by Suramire on 2018/1/24.
 */

public abstract class ResponseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }



}
