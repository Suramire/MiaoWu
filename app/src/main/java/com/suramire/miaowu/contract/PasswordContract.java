package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.User;

import rx.Observable;

public interface PasswordContract {

    interface Model<T>{
        Observable<T> modify(User user);

        Observable<T> checkPhone(String phoneNumber);
    }

    interface View extends BaseView{
        User getUser();

        String getPhoneNumber();

        void onCheckPhoneSuccess();

        void onCheckPhoneFailed();
    }

    interface Presenter extends BasePresenter<View>{
        //执行修改密码操作
        void modify();
        //判断手机号是否存在
        void checkPhone();
    }

}
