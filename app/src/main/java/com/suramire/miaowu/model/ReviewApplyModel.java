package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ReviewApplyContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

public class ReviewApplyModel implements ReviewApplyContract.Model {
    @Override
    public Observable<Void> reviewApply(int cid, int flag) {
        Catinfo catinfo = new Catinfo();
        catinfo.setId(cid);
        catinfo.setIsAdopted(flag);
        return ApiLoader.reviewApplyCat(catinfo)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<User> getUserInfo(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getUser(user).
                map(new ResponseFunc<User>());
    }

    @Override
    public Observable<Catinfo> getCatInfo(int cid) {
        Catinfo catinfo = new Catinfo();
        catinfo.setId(cid);
        return ApiLoader.getOneCat(catinfo)
                .map(new ResponseFunc<Catinfo>());
    }
}
