package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.http.ApiLoader;

import rx.Observable;

/**
 * Created by Suramire on 2017/11/2.
 */

public class ReplyModel implements ReplyContract.Model {
    @Override
    public Observable postReply(Reply reply) {
        return ApiLoader.addReply(reply);
    }

    @Override
    public Observable deleteReply(Reply reply) {
        return ApiLoader.deleteReply(reply);
    }
}
