package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Note;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/27.
 */

public interface NoteContract {
    interface Model<T> {
        Observable<T> getNotesByUser(int uid);

        Observable<T> getAllNotesByUser(int uid);

        Observable<T> getUnverifyNotes();

    }

    interface View extends BaseView {

        void onGetNoteByUserSuccess(List<Note> noteLis);

    }

    interface Presenter extends BasePresenter<View> {

        void getNotesByUser(int uid);
        //包括锁定的帖子
        void getAllNotesByUser(int uid);

        void getUnverifyNotes();
    }
}
