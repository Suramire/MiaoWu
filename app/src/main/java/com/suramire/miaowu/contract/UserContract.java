package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.User;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/27.
 */

public interface UserContract {
    interface Model<T> {

        Observable<T> getUserInfo(int uid);

        Observable<T> getUserNoteCount(int uid);

        Observable<T> getUserFollowCount(int uid);

        Observable<T> getUserFollowerCount(int uid);
    }

    interface View extends BaseView {

        int getUid();

        void onGetInfoSuccess(User userinfo);

        void onGetUserFollowCountSuccess(int count);

        void onGetUserFollowerCountSuccess(int count);

        void onGetUserNoteCountSuccess(int count);


    }

    interface Presenter extends BasePresenter<View> {

        //根据用户id获取
        //单个用户信息
        void getUserInfo();
        //用户关注数
        void getUserFollowCount();
        //用户粉丝数
        void getUserFollowerCount();
        //用户发帖数
        void getUserNoteCount();


    }
}
