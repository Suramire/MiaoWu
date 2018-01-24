package com.suramire.miaowu.presenter;

import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ReplyModel;
import com.suramire.miaowu.util.ToastUtil;

/**
 * Created by Suramire on 2017/11/2.
 */

public class ReplyPresenter implements ReplyContract.Presenter {

    private final ReplyModel mReplyModel;
    private ReplyContract.View mView;

    public ReplyPresenter() {
        mReplyModel = new ReplyModel();
    }

    @Override
    public void postReply() {
        mView.showLoading();
        mReplyModel.postReply(mView.getReplyInfo())
                .subscribe(new ResponseSubscriber() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        mView.cancelLoading();
                        mView.onAddSuccess();
                    }
                });
//
    }

    @Override
    public void deleteReply() {
        mView.showLoading();
        mReplyModel.deleteReply(mView.getReplyInfo())
                .subscribe(new ResponseSubscriber() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }
                    @Override
                    public void onNext(Object o) {
                        mView.cancelLoading();
                        mView.onDeleteSuccess();
                    }
                });
    }

    @Override
    public void attachView(ReplyContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
