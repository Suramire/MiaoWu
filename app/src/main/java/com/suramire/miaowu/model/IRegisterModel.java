package com.suramire.miaowu.model;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/10/22.
 */

public interface IRegisterModel {

    void validatePhoneNumber(String phoneNumber, OnGetResultListener onValidationListener);

    void validateRegisterInformation(String pnhoneNumber,String userName, String password,String rePassword, OnGetResultListener onValidationListener);

}
