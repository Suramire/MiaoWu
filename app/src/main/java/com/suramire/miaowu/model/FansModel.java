package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.FansContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/30.
 */

public class FansModel implements FansContract.Model {
    @Override
    public Observable<List<User>> getFollow(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUserFollow(user)
                .map(new ResponseFunc<List<User>>());
    }

    @Override
    public Observable<List<User>> getFollower(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUserFollower(user)
                .map(new ResponseFunc<List<User>>());
    }
}
