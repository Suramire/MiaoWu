package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.Reply;

/**
 * Created by Suramire on 2017/11/2.
 */

public interface RepleyContract {
    interface Model {
        void postReply(Reply reply,OnGetResultListener listener);

        void getReply(OnGetResultListener listener, Object... objects);
    }

    interface View {
        void showLoading();

        void cancelLoading();

        void onSuccess(Object object);

        void onFailure(String failureMessage);

        void onError(String errorMessage);

        Reply getReplyInfo();
    }

    interface Presenter {
        void postReply();

        void getReply();
    }
}
