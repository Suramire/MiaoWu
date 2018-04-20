package com.suramire.miaowu.presenter;

import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ReplyModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/11/2.
 */

public class ReplyPresenter implements ReplyContract.Presenter {

    private final ReplyModel mReplyModel;
    private ReplyContract.View mView;
    private CompositeSubscription compositeSubscription;

    public ReplyPresenter() {
        mReplyModel = new ReplyModel();
    }

    @Override
    public void postReply() {
        mView.showLoading();
        Subscription subscribe = mReplyModel.postReply(mView.getReplyInfo())
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
        compositeSubscription.add(subscribe);

//
    }


    @Override
    public void unPassNote() {
        mView.showLoading();
        Subscription subscribe = mReplyModel.unPassNote(mView.getUnPassInfo())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("发送驳回过程出错:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onUnpassSuccess();
                    }
                });
        compositeSubscription.add(subscribe);
    }


    @Override
    public void attachView(ReplyContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
