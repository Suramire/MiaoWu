package com.suramire.miaowu.model;


import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/27.
 */

public class UserModel implements UserContract.Model {
    @Override
    public Observable<User> getUserInfo(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUser(user)
                .map(new ResponseFunc<User>());
    }

    @Override
    public Observable<Integer> getUserNoteCount(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUserNoteCount(user)
                .map(new ResponseFunc<Integer>());
    }

    @Override
    public Observable<Integer> getUserFollowCount(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUserFollowCount(user)
                .map(new ResponseFunc<Integer>());
    }

    @Override
    public Observable<Integer> getUserFollowerCount(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUserFollowerCount(user)
                .map(new ResponseFunc<Integer>());
    }
}
