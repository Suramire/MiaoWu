package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/30.
 */

public interface FansContract {

    interface Model<T> {

        Observable<T> getFollow(int uid);

        Observable<T> getFollower(int uid);

    }

    interface View extends BaseView {

        int getUid();

    }

    interface Presenter extends BasePresenter<View> {
        //获取关注用户的信息
        void getFollow();
        //获取粉丝用户的信息
        void getFollower();

    }
}
