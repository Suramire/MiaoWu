package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.User;

import rx.Observable;

public interface PasswordContract {

    interface Model<T>{
        Observable<T> modify(User user);
    }

    interface View extends BaseView{
        User getUser();
    }

    interface Presenter extends BasePresenter<View>{
        void modify();
    }

}
