package com.suramire.miaowu.model;

import android.text.TextUtils;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<User> doLogin(final String username, final String password) {
        //信息完整性验证
        if(TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
//            onLoginListener.onFailure("请将帐号信息补充完整");
            return null;
        }else{
            User user = new User();
            user.setNickname(username);
            user.setPassword(password);
            return ApiLoader.login(user)
                    .map(new ResponseFunc<User>());
        }
    }

    @Override
    public Observable<User> getUserInfo(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUser(user)
                .map(new ResponseFunc<User>());
    }
}
