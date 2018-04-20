package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;

import rx.Observable;

/**
 * Created by Suramire on 2017/11/2.
 */

public interface ReplyContract {
    interface Model<T> {
        //发表新评论
        Observable<T> postReply(Reply reply);

        Observable<T> unPassNote(Note note);


    }

    interface View extends BaseView {
        void onDeleteSuccess();

        void onAddSuccess();

        Reply getReplyInfo();
        //获取驳回的帖子编号以及驳回理由
        Note getUnPassInfo();

        void onUnpassSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void postReply();


        //驳回帖子
        void unPassNote();


    }
}
