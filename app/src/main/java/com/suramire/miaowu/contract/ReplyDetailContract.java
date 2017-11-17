package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/11/17.
 */

public interface ReplyDetailContract {
    interface Model {
        void getReplyList(int floorid, OnGetResultListener listener);
    }

    interface View {
        void showLoading();

        void cancelLoading();

        void onGetReplyListSuccess(Object object);

        void onGetReplyListFaiure(String failureMessage);

        void onGetReplyListError(String errorMessage);

        int getFloorId();
    }

    interface Presenter {
        void getReplyList();
    }
}
