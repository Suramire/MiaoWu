package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface RegisterContract {
    interface Model<T> {
        Observable<T> validatePhoneNumber(String phoneNumber);

        Observable<T> validateRegisterInformation(String phoneNumber,String userName, String password,
                                         String rePassword);
    }

    interface View extends BaseView {

        void onPhoneSuccess();

        String getPhoneNumber();

        String getUserName();

        String getPassword();

        String getRePassword();

        void goStep0();

        void goStep1();

        void goStep2();
    }

    interface Presenter extends BasePresenter<View> {
        void validatePhoneNumber();

        void validateInformation();
    }
}
