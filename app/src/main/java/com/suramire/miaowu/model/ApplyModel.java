package com.suramire.miaowu.model;

import com.suramire.miaowu.base.App;
import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.contract.ApplyContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/4/9.
 */

public class ApplyModel implements ApplyContract.Model {
    @Override
    public Observable<List<Apply>> getApplys(int uid) {
        Apply apply = new Apply();
        apply.setUid(uid);
        return ApiLoader.getallApply(apply)
                .map(new ResponseFunc<List<Apply>>());
    }
}
