package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
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
                        ToastUtil.showLongToastCenter("获取帖子详情失败:" + throwable.getMessage());
//                        getCatInfo();
                        mView.cancelLoading();
                    }

                    @Override
                    public void onNext(Note note) {
                        mView.onGetNoteInfoSuccess(note);
//                        if(note.getType()==2){
//                            getCatInfo();
//                        }else{
//
//                        }
                        getReply();

                    }
                });

        compositeSubscription.add(subscribe);

    }

    @Override
    public void getReply() {
        Subscription subscribe = noteDetailModel.getNoteReply(mView.getNoteId())
                .subscribe(new ResponseSubscriber<List<M>>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<M> multis) {
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
                        ToastUtil.showLongToastCenter("获取帖子配图失败:" + throwable.getMessage());
                        mView.cancelLoading();
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

//    @Override
//    public void getCatInfo() {
//        Subscription subscribe = noteDetailModel.getCatInfo(mView.getNoteId())
//                .subscribe(new ResponseSubscriber<Catinfo>() {
//                    @Override
//                    public void onError(Throwable throwable) {
//                        L.e("获取猫咪信息失败:"+throwable.getMessage());
//                        getReply();
//                    }
//
//                    @Override
//                    public void onNext(Catinfo catinfo) {
//                        mView.onGetCatInfoSuccess(catinfo);
//                        getReply();
//                    }
//                });
//        compositeSubscription.add(subscribe);
//
//    }

    @Override
    public void getUserInfo() {
        Subscription subscribe = noteDetailModel.getUserInfo(mView.getUserId())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showLongToastCenter("获取作者信息失败:" + throwable.getMessage());
                        mView.cancelLoading();
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

//    @Override
//    public void reviewApply() {
//        mView.showLoading();
//        Subscription subscribe = noteDetailModel.reviewApply(mView.getApply())
//                .subscribe(new ResponseSubscriber<Void>() {
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.cancelLoading();
//                        ToastUtil.showLongToastCenter("申请时出错：" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Void aVoid) {
//                        mView.cancelLoading();
//                        mView.onApplySuccess();
//                    }
//                });
//        compositeSubscription.add(subscribe);
//    }

    @Override
    public void passNote() {
        mView.showLoading();
        Subscription subscribe = noteDetailModel.passNote(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("发送审核过程出错:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onPassSuccess();
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void lockNote() {
        mView.showLoading();
        Subscription subscribe = noteDetailModel.lockNote(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("锁定帖子时出错:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onLockSuccess();
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void unlockNote() {
        mView.showLoading();
        Subscription subscribe = noteDetailModel.unlockNote(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("解锁帖子失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onUnlockSuccess();
                    }
                });
        compositeSubscription.add(subscribe);
    }


    @Override
    public void deleteNote() {
        mView.showLoading();
        Subscription subscribe = noteDetailModel.deleteNote(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("删除帖子失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onDeleteSuccess();
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void increaseNouteCount() {
        Subscription subscribe = noteDetailModel.increaseNoteCount(mView.getNoteId())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

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
