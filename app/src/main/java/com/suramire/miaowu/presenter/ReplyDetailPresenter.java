package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.model.ReplyDetailModel;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailPresenter implements ReplyDetailContract.Presenter {

    private final ReplyDetailModel mReplyDetailModel;
    private final Handler mHandler;
    private ReplyDetailContract.View mView;

    public ReplyDetailPresenter(ReplyDetailContract.View view) {
        mView = view;
        mReplyDetailModel = new ReplyDetailModel();
        mHandler = new Handler();
    }

    @Override
    public void getReplyList() {
        mView.showLoading();
        mReplyDetailModel.getReplyList(mView.getFloorId(), new OnGetResultListener() {
            @Override
            public void onSuccess(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onGetReplyListSuccess(object);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onGetReplyListFaiure(failureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onGetReplyListError(errorMessage);
                    }
                });
            }
        });
    }

    @Override
    public void getUsersById() {

    }
}
