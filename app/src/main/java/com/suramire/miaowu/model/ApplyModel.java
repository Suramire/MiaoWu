package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.AdoptContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/4/9.
 */

public class ApplyModel implements AdoptContract.Model {
    @Override
    public Observable<List<Catinfo>> getAdoptHistory(int uid) {
        Catinfo catinfo = new Catinfo();
        catinfo.setUid(uid);
        return ApiLoader.getAdoptedCat(catinfo)
                .map(new ResponseFunc<List<Catinfo>>());
    }
}
