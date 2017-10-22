package com.suramire.miaowu.view;

/**
 * Created by Suramire on 2017/10/22.
 */

public interface IRegisterView {
    void showLoading();

    void cancelLoading();

    void onRegisterSuccess();

    void onRegisterFailure(String failureMessage);

    void onRegisterError(String errorMessage);

    String getPhoneNumber();

    String getValidationNumber();

    String getUserName();

    String getPassword();

    String getRePassword();

    void goStep0();

    void goStep1();

    void goStep2();
}
