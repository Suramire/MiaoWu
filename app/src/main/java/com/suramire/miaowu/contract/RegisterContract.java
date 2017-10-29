package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface RegisterContract {
    interface Model {
        void validatePhoneNumber(String phoneNumber, OnGetResultListener listener);

        void validateRegisterInformation(String phoneNumber,String userName, String password,
                                         String rePassword, OnGetResultListener listener);
    }

    interface View {
        void showLoading();

        void cancelLoading();

        void onRegisterSuccess();

        void onRegisterFailure(String failureMessage);

        void onRegisterError(String errorMessage);

        String getPhoneNumber();

        String getUserName();

        String getPassword();

        String getRePassword();

        void goStep0();

        void goStep1();

        void goStep2();
    }

    interface Presenter {
        void validatePhoneNumber(final OnGetResultListener onPhoneValidListener);

        void validateInformation();
    }
}
