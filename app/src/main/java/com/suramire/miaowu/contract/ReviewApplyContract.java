package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.User;

import rx.Observable;

public interface ReviewApplyContract {

    interface Model<T>{

        Observable<T> reviewApply(int cid, int flag);

        Observable<T> getUserInfo(int uid);

        Observable<T> getCatInfo(int cid);
    }

    interface View extends BaseView{

        //获取操作的标志位
        int getFlag();

        int getUid();

        int getCid();


        void onGetUserSuccess(User user);

        void onGetCatSuccess(Catinfo catinfo);

    }

    interface Presenter extends BasePresenter<View>{

        void reviewApply();

        void getUserInfo();

        void getCatInfo();
    }
}
