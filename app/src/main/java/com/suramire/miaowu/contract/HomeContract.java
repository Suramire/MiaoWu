package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface HomeContract {
    interface Model<T> {
        Observable<T> getData(int start, int end);
    }

    interface View extends BaseView {
        void clearData();
    }

    interface Presenter extends BasePresenter<View> {
        void getData();
    }
}
