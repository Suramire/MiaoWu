package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.NoteDetailModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/31.
 */

public class NoteDetailPresenter implements NoteDetailContract.Presenter {
    private final NoteDetailModel mNoteDetailModel;
    private NoteDetailContract.View mView;
    private CompositeSubscription compositeSubscription;

    public NoteDetailPresenter() {
        mNoteDetailModel = new NoteDetailModel();
    }


    @Override
    public void getReply() {
        mView.showLoading();
        Subscription subscribe = mNoteDetailModel.getNoteReply(mView.getNoteId())
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
        compositeSubscription.add(subscribe);

    }

    @Override
    public void thumb() {
        Subscription subscribe = mNoteDetailModel.thumb(mView.getNoteId())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mView.onThumbSuccess();
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void attachView(NoteDetailContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }
}
