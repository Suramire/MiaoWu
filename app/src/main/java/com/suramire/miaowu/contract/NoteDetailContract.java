package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
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

        Observable<T> getUserInfo(int noteId);

        Observable<T> passNote(int noteId);

        Observable<T> lockNote(int noteId);

        Observable<T> unlockNote(int noteId);

        Observable<T> deleteNote(int noteId);


        Observable<T> increaseNoteCount(int noteId);


    }

    interface View extends BaseView {

        int getUserId();

        int getNoteId();

        void onThumbSuccess();

        void onGetNoteInfoSuccess(Note note);
        //成功获取作者信息时的回调
        void onGetUserInfoSuccess(User user);

        void onOnGetPictureSuccess(List<String> paths);


        void onPassSuccess();

        void onLockSuccess();

        void onUnlockSuccess();

        void onDeleteSuccess();


    }

    interface Presenter extends BasePresenter<View> {

        void getNoteInfo();

        void getReply();
        //点赞操作
        void thumb();
        //获取帖子配图路径
        void getPictue();

        void getUserInfo();
        //审核通过
        void passNote();
        //锁定帖子
        void lockNote();
        //解锁帖子
        void unlockNote();
        //更新帖子
//        void updateNote();
        //删除帖子
        void deleteNote();
        //增加帖子访问量
        void increaseNouteCount();


    }
}
