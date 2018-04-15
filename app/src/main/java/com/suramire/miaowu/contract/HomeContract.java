package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface HomeContract {
    interface Model<T> {
        Observable<T> getData(int start, int end);

        Observable<T> listCats();


    }

    interface View extends BaseView {
        void clearData();

        void onGetCatListSuccess(List<Catinfo> catinfos);

        void onGetNoteListSuccess(List<M> mList);

    }

    interface Presenter extends BasePresenter<View> {
        void getData();

        void listCats();
    }
}
