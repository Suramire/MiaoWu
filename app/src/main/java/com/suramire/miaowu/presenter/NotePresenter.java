package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.NoteContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.NoteModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/1/27.
 */

public class NotePresenter implements NoteContract.Presenter {
    private NoteModel noteModel;
    private NoteContract.View mView;
    private CompositeSubscription compositeSubscription;


    public NotePresenter() {
        noteModel = new NoteModel();
    }

    @Override
    public void attachView(NoteContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }

    @Override
    public void getNotesByUser(int uid) {
        mView.showLoading();
        Subscription subscribe = noteModel.getNotesByUser(uid)
                .subscribe(new ResponseSubscriber<List<Note>>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取帖子列表失败:" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        mView.cancelLoading();
                        mView.onGetNoteByUserSuccess(notes);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getAllNotesByUser(int uid) {
        mView.showLoading();
        Subscription subscribe = noteModel.getAllNotesByUser(uid)
                .subscribe(new ResponseSubscriber<List<Note>>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取帖子列表失败:" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        mView.cancelLoading();
                        mView.onGetNoteByUserSuccess(notes);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getUnverifyNotes() {
        mView.showLoading();
        Subscription subscribe = noteModel.getUnverifyNotes()
                .subscribe(new ResponseSubscriber<List<Note>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取帖子列表失败:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        mView.cancelLoading();
                        mView.onSuccess(notes);
                    }
                });
        compositeSubscription.add(subscribe);
    }
}
