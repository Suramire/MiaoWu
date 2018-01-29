package com.suramire.miaowu.model;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailModel implements ReplyDetailContract.Model {

    @Override
    public void getUsersById(List ids, OnGetResultListener listener) {

    }

    @Override
    public Observable<List<Multi0>> listReplyDetail(int floorId) {
        Reply reply = new Reply();
        reply.setFloorid(floorId);
        return ApiLoader.listReplyDetail(reply).map(new ResponseFunc<List<Multi0>>());
    }

}
