package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.model.HomeModel;
import com.suramire.miaowu.pojo.Note;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.view.IHomeView;

import java.util.List;

/**
 * Created by Suramire on 2017/10/29.
 */

public class HomePresenter {
    private final HomeModel mHomeModel;
    private final Handler mHandler;
    private final IHomeView mIHomeView;

    public HomePresenter(IHomeView IHomeView) {
        mIHomeView = IHomeView;
        mHomeModel = new HomeModel();
        mHandler = new Handler();
    }

    public void getData(){
        mIHomeView.clearData();
        mIHomeView.startLoading();
        mHomeModel.getData(new OnGetResultListener() {
            @Override
            public void onSuccess(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIHomeView.stopLoading();
                        List<Note> notes = GsonUtil.jsonToList(object.toString(), Note.class);
                        mIHomeView.onGetSuccess(notes);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIHomeView.stopLoading();
                        mIHomeView.onGetFailure(failureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIHomeView.stopLoading();
                        mIHomeView.onGetError(errorMessage);
                    }
                });
            }
        });
    }
}
