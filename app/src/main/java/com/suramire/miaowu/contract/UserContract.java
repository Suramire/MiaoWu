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

        Observable<T> follow(int uid1,int uid2);

        Observable<T> unfollow(int uid1, int uid2);

        Observable<T> getRelationship(int uid,int uid2);


    }

    interface View extends BaseView {

        //获取当前页的用户id
        int getUid();

        void onGetInfoSuccess(User userinfo);

        void onGetUserFollowCountSuccess(int count);

        void onGetUserFollowerCountSuccess(int count);

        void onGetUserNoteCountSuccess(int count);

        void onGetRelationshipSuccess(int type);




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
        //获取当前页面的用户与登录用户的关系
        void getRelationship();
        //关注用户
        void follow();
        //取消关注
        void unfollow();

    }
}
