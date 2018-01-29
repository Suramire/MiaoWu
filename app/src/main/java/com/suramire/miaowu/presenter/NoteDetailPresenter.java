package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.NoteDetailModel;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/31.
 */

public class NoteDetailPresenter implements NoteDetailContract.Presenter {
    private final NoteDetailModel noteDetailModel;
    private NoteDetailContract.View mView;
    private CompositeSubscription compositeSubscription;

    public NoteDetailPresenter() {
        noteDetailModel = new NoteDetailModel();
    }


    @Override
    public void getNoteInfo() {
        Subscription subscribe = noteDetailModel.getNoteDetail(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Note>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShortToastCenter("获取帖子详情失败:" + throwable.getMessage());
//                        getCatInfo();
                    }

                    @Override
                    public void onNext(Note note) {
                        mView.onGetNoteInfoSuccess(note);
                        if(note.getType()==2){
                            getCatInfo();
                        }else{
                            getReply();
                        }

                    }
                });

        compositeSubscription.add(subscribe);

    }

    @Override
    public void getReply() {
        mView.showLoading();
        Subscription subscribe = noteDetailModel.getNoteReply(mView.getNoteId())
                .subscribe(new ResponseSubscriber<List<Multi0>>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<Multi0> multis) {
                        mView.cancelLoading();
                        mView.onSuccess(multis);
                    }

                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void thumb() {
        Subscription subscribe = noteDetailModel.thumb(mView.getNoteId())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mView.onThumbSuccess();
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void getPictue() {
        mView.showLoading();
        Subscription subscribe = noteDetailModel.getPicture(mView.getNoteId())
                .subscribe(new ResponseSubscriber<List<String>>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShortToastCenter("获取帖子配图失败:" + throwable.getMessage());
                        getUserInfo();
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mView.onOnGetPictureSuccess(strings);
                        getUserInfo();
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getCatInfo() {
        Subscription subscribe = noteDetailModel.getCatInfo(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Catinfo>() {
                    @Override
                    public void onError(Throwable throwable) {
                        L.e("获取猫咪信息失败:"+throwable.getMessage());
                        getReply();
                    }

                    @Override
                    public void onNext(Catinfo catinfo) {
                        mView.onGetCatInfoSuccess(catinfo);
                        getReply();
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void getUserInfo() {
        Subscription subscribe = noteDetailModel.getUserInfo(mView.getUserId())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShortToastCenter("获取作者信息失败:" + throwable.getMessage());
                        getNoteInfo();
                    }

                    @Override
                    public void onNext(User user) {
                        mView.onGetUserInfoSuccess(user);
                        getNoteInfo();
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
