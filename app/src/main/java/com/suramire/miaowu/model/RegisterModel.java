package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.RegisterContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<User>  validatePhoneNumber(final String phoneNumber) {
        User user = new User();
        user.setPhonenumber(phoneNumber);
        return ApiLoader.checkPhoneUser(user)
                .map(new ResponseFunc<User>());
    }

    @Override
    public Observable<User> validateRegisterInformation(String phoneNumber, String userName, String password, final String rePassword) {
        User user = new User(phoneNumber, userName, password,0,0);
        return ApiLoader.addUser(user)
                .map(new ResponseFunc<User>());

    }

}
