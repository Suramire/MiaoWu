package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.PasswordContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

public class PasswordModel implements PasswordContract.Model {
    @Override
    public Observable<Void> modify(User user) {
        return ApiLoader.modifyPassword(user)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<User> checkPhone(String phoneNumber) {
        User user = new User();
        user.setPhonenumber(phoneNumber);
        return ApiLoader.checkPhoneUser(user)
                .map(new ResponseFunc<User>());
    }
}
