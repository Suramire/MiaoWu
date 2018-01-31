package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ReplyDetailModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailPresenter implements ReplyDetailContract.Presenter {

    private final ReplyDetailModel mReplyDetailModel;
    private ReplyDetailContract.View mView;
    private CompositeSubscription compositeSubscription;

    public ReplyDetailPresenter() {
        mReplyDetailModel = new ReplyDetailModel();
    }


    @Override
    public void getUsersById() {

    }

    @Override
    public void attachView(ReplyDetailContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }


    @Override
    public void listReplyDetail() {
        mView.showLoading();
        Subscription subscribe = mReplyDetailModel.listReplyDetail(mView.getFloorId())
                .subscribe(new ResponseSubscriber<List<Multi0>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取评论详情失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Multi0> multi0s) {
                        mView.cancelLoading();
                        mView.onSuccess(multi0s);
                    }
                });
        compositeSubscription.add(subscribe);

    }
}
