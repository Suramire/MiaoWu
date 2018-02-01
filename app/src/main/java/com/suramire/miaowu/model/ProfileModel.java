package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;
import com.suramire.miaowu.util.SPUtils;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public class ProfileModel implements ProfileContract.Model {
//    @Override
//    public Observable<User> getProfile(int id) {
//        int uid = (int) SPUtils.get("uid", 0);
//        User tUser = new User();
//        tUser.setId(uid);
//        return ApiLoader.getUser(tUser)
//                .map(new ResponseFunc<User>());
//    }

    @Override
    public Observable<User> updateProfile(User user) {
        return ApiLoader.updateProfile(user)
                .map(new ResponseFunc<User>());
    }
}
