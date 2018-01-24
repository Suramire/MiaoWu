package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface ProfileContract {
    interface Model<T> {
        Observable<T> getProfile(int id);
    }

    interface View extends BaseView {

        int getUid();
    }

    interface Presenter extends BasePresenter<View> {
        void getProfile();
    }
}
