package com.suramire.miaowu.presenter;

import android.text.TextUtils;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.SearchContract;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.SearchModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/3/26.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private final SearchModel searchModel;
    private SearchContract.View mView;
    private CompositeSubscription compositeSubscription;


    public SearchPresenter() {
        searchModel = new SearchModel();
    }

    @Override
    public void attachView(SearchContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }

    @Override
    public void searchNote() {
        if(!TextUtils.isEmpty(mView.getQuery())){
            mView.showLoading();
            Subscription subscribe = searchModel.searchNote(mView.getQuery())
                    .subscribe(new ResponseSubscriber<List<Note>>() {
                        @Override
                        public void onError(Throwable e) {
                            mView.cancelLoading();
                            ToastUtil.showShortToastCenter("搜索帖子信息时出错：" + e.getMessage());
                        }

                        @Override
                        public void onNext(List<Note> notes) {
                            mView.cancelLoading();
                            mView.onNoteSuccess(notes);
                        }
                    });
            compositeSubscription.add(subscribe);
        }
    }

    @Override
    public void searchUser() {
        if(!TextUtils.isEmpty(mView.getQuery())){
            mView.showLoading();
            Subscription subscribe = searchModel.searchUser(mView.getQuery())
                    .subscribe(new ResponseSubscriber<List<User>>() {
                        @Override
                        public void onError(Throwable e) {
                            mView.cancelLoading();
                            ToastUtil.showShortToastCenter("搜索用户信息出错：" + e.getMessage());
                        }

                        @Override
                        public void onNext(List<User> users) {
                            mView.cancelLoading();
                            mView.onUserSuccess(users);
                        }
                    });
            compositeSubscription.add(subscribe);
        }

    }
}
