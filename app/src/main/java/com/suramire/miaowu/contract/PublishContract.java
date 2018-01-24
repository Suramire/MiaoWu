package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface PublishContract {
    interface Model<T> {
        Observable<T> publish(Catinfo catinfo, String title, String content, List<String> pictues);

        Observable<T> uploadPicturePath(Object object, List<String> pictues);
    }

    interface View extends BaseView {


        String getNoteTitle();

        String getNoteContent();

        Catinfo getCatinfo();

        //帖子配图的路径
        List<String> getPicturePaths();
    }

    interface Presenter extends BasePresenter<View> {
        void publish();
    }
}
