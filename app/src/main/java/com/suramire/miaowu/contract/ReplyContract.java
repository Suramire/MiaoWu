package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Reply;

import rx.Observable;

/**
 * Created by Suramire on 2017/11/2.
 */

public interface ReplyContract {
    interface Model<T> {
        //发表新评论
        Observable<T> postReply(Reply reply);
        //删除评论
        Observable<T> deleteReply(Reply reply);


    }

    interface View extends BaseView {
        void onDeleteSuccess();

        void onAddSuccess();

        Reply getReplyInfo();
    }

    interface Presenter extends BasePresenter<View> {
        void postReply();

        void deleteReply();


    }
}
