package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/31.
 */

public interface MainContract {
    interface Model<T> {

        Observable<T> getNoticationCount(int uid);
    }

    interface View extends BaseView {

        void onGetNotificationCountSuccess(int count);
    }

    interface Presenter extends BasePresenter<View> {

        void getNotificationCount(int uid);
    }
}
