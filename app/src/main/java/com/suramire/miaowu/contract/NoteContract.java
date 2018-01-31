package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/27.
 */

public interface NoteContract {
    interface Model<T> {
        Observable<T> getNotesByUser(int uid);

        Observable<T> getUnverifyNotes();

    }

    interface View extends BaseView {


    }

    interface Presenter extends BasePresenter<View> {

        void getNotesByUser(int uid);

        void getUnverifyNotes();
    }
}
