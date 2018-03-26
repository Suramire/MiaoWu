package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/3/26.
 */

public interface SearchContract {
    interface Model<T> {
        Observable<T> searchNote(String query);
    }

    interface View extends BaseView {


        void onNoteSuccess(List<Note> notes);

        String getQuery();

    }

    interface Presenter extends BasePresenter<SearchContract.View> {

        void searchNote();


    }
}
