package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.Note;

import java.util.List;

/**
 * Created by Suramire on 2017/10/31.
 */

public interface NoteDetailContract {
    interface Model {
        void getNoteDetail(int noteId, OnGetResultListener listener);

        void getPicture(int noteId, OnGetResultListener listener);

        void getNoteReply(int noteId, OnGetResultListener listener);

        void thumb(int noteId, OnGetResultListener listener);

    }

    interface View {
        void showLoading();

        void cancelLoading();

        int getNoteId();

        void onGetSuccess(Note note);

        void onGetFailure(String failureMessage);

        void onGetError(String errorMessage);

        void onGetPictureSuccess(List<String> pictures);

        void onGetPictureFailure(String failureMessage);

        void onGetPictureError(String errorMessage);

        void onGetReplySuccess(Object object);

        void onGetReplyFailure(String failureMessage);

        void onGetReplyError(String errorMessage);

        void onThumbSuccess();
    }

    interface Presenter {
        void getData();

        void getPicture();

        void getReply();

        void thumb();
    }
}
