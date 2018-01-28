package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/31.
 */

public interface NoteDetailContract {
    interface Model<T> {
        Observable<T> getNoteDetail(int noteId);

        Observable<T> getPicture(int noteId);

        Observable<T> getNoteReply(int noteId);

        Observable<T> thumb(int noteId);

        Observable<T> getCatInfo(int noteId);

        Observable<T> getUserInfo(int noteId);

    }

    interface View extends BaseView {

        int getUserId();

        int getNoteId();

        void onThumbSuccess();

        void onGetNoteInfoSuccess(Note note);

        void onGetUserInfoSuccess(User user);

        void onOnGetPictureSuccess(List<String> paths);

        void onGetCatInfoSuccess(Catinfo catinfo);
    }

    interface Presenter extends BasePresenter<View> {

        void getNoteInfo();

        void getReply();
        //点赞操作
        void thumb();
        //获取帖子配图路径
        void getPictue();
        //获取猫咪信息
        void getCatInfo();

        void getUserInfo();
    }
}
