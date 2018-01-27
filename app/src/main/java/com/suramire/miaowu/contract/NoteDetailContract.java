package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;

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

    }

    interface View extends BaseView {

        int getNoteId();

        void onThumbSuccess();

        void onOnGetPictureSuccess();

        void onGetCatInfoSuccess();
    }

    interface Presenter extends BasePresenter<View> {

        void getReply();
        //点赞操作
        void thumb();
        //获取帖子配图路径
        void getPictue();
        //获取猫咪信息
        void getCatInfo();
    }
}
