package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.contract.RepleyContract;
import com.suramire.miaowu.model.ReplyModel;

/**
 * Created by Suramire on 2017/11/2.
 */

public class ReplyPresenter implements RepleyContract.Presenter {

    private final ReplyModel mReplyModel;
    private final Handler mHandler;
    private RepleyContract.View mView;

    public ReplyPresenter(RepleyContract.View view) {
        mView = view;
        mReplyModel = new ReplyModel();
        mHandler = new Handler();
    }

    @Override
    public void postReply() {
        mView.showLoading();
        mReplyModel.postReply(mView.getReplyInfo(),new OnGetResultListener() {
            @Override
            public void onSuccess(Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onSuccess(null);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onFailure(failureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onError(errorMessage);
                    }
                });
            }
        });
    }

    @Override
    public void deleteReply() {
        mView.showLoading();
        mReplyModel.deleteReply(mView.getReplyInfo(), new OnGetResultListener() {
            @Override
            public void onSuccess(Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onSuccess(null);
                    }
                });
            }

            @Override
            public void onFailure(String failureMessage) {

            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.cancelLoading();
                        mView.onError(errorMessage);
                    }
                });
            }
        });
    }

}
