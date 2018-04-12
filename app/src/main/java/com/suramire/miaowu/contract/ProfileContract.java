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

        Observable<T> updateAvater(int uid);

        Observable<T> uploadAvater(int uid,String path);
    }

    interface View extends BaseView {

        int getUid();

        User getUser();

        void onUpdateSuccess(User user);

        String getAvaterPath();

        void onUpdateAvaterSuccess();


    }

    interface Presenter extends BasePresenter<View> {
        //获取用户信息
        void getProfile();
        //更新用户信息
        void updateProfile();
        //更新用户头像字段
        void updateAvater();

        void uploadAvater();
    }
}
