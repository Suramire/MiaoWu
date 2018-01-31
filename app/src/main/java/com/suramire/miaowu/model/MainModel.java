package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.MainContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/31.
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable<Integer> getNoticationCount(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getunreadCount(user)
                .map(new ResponseFunc<Integer>());
    }
}
