package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ReplyDetailModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailPresenter implements ReplyDetailContract.Presenter {

    private final ReplyDetailModel mReplyDetailModel;
    private ReplyDetailContract.View mView;

    public ReplyDetailPresenter() {
        mReplyDetailModel = new ReplyDetailModel();
    }

    @Override
    public void getReplyList() {
        mView.showLoading();
        mReplyDetailModel.getReplyList(mView.getFloorId())
                .subscribe(new ResponseSubscriber<List<Multi>>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<Multi> multis) {
                        mView.cancelLoading();
                        mView.onSuccess(multis);
                    }
                });
    }

    @Override
    public void getUsersById() {

    }

    @Override
    public void attachView(ReplyDetailContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
