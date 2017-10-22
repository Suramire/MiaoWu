package com.suramire.miaowu.model;

import com.suramire.miaowu.listener.OnEventCodeListener;
import com.suramire.miaowu.listener.OnValidationListener;

/**
 * Created by Suramire on 2017/10/22.
 */

public interface IRegisterModel {

    void validatePhoneNumber(String phoneNumber, OnValidationListener onValidationListener);

    void validateValidationNumber(String validataionNumber, OnValidationListener onValidationListener);

    void validateRegisterInformation(String pnhoneNumber,String userName, String password,String rePassword, OnValidationListener onValidationListener);

    void registerEventHandler(OnEventCodeListener onEventCodeListener);

    void unregisterEventHandler();
}
