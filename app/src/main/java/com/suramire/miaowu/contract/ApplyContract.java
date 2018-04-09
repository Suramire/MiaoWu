package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * 领养记录
 */

public interface ApplyContract {
    interface Model<T> {

        Observable<T> getApplys(int uid);


    }

    interface View extends BaseView {

        int getUid();



    }

    interface Presenter extends BasePresenter<ApplyContract.View> {

        void getApplys();

    }
}
