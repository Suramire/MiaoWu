package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<List<M>> getData(int start, int end) {
        return ApiLoader.getMultiNotes(null)
                .map(new ResponseFunc<List<M>>());
    }

    @Override
    public Observable<List<Catinfo>> listCats() {
        return ApiLoader.getAllCat()
                .map(new ResponseFunc<List<Catinfo>>());
    }


}
