package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Note;
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
    public Observable<List<Multi>> getData(int type,int start,int end) {
        Note note = new Note();
        note.setType(type);
        return ApiLoader.getMultiNotes(note)
                .map(new ResponseFunc<List<Multi>>());
    }


}
