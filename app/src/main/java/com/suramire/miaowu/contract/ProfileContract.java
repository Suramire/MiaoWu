package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.User;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface ProfileContract {
    interface Model<T> {
        Observable<T> getProfile(int id);

        Observable<T> updateProfile(User user);
    }

    interface View extends BaseView {

        int getUid();

        User getUser();

        void onUpdateSuccess(User user);


    }

    interface Presenter extends BasePresenter<View> {
        //获取用户信息
        void getProfile();
        //更新用户信息
        void updateProfile();
    }
}
