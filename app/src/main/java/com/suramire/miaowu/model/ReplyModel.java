package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

/**
 * Created by Suramire on 2017/11/2.
 */

public class ReplyModel implements ReplyContract.Model {
    @Override
    public Observable<Reply> postReply(Reply reply) {
        return ApiLoader.addReply(reply).map(new ResponseFunc<Reply>());
    }


    @Override
    public Observable<Void> unPassNote(Note note) {
        return ApiLoader.unPassNote(note)
                .map(new ResponseFunc<Void>());
    }


}
