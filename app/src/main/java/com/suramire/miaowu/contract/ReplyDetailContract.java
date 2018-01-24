package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.base.OnGetResultListener;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/11/17.
 */

public interface ReplyDetailContract {
    interface Model<T> {
        Observable<T> getReplyList(int floorid);

        void getUsersById(List<Integer> ids,OnGetResultListener listener);
    }

    interface View  extends BaseView{

        int getFloorId();
    }

    interface Presenter extends BasePresenter<View> {
        void getReplyList();

        void getUsersById();
    }
}
