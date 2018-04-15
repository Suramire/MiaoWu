package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Follow;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.UserModel;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2018/1/27.
 */

public class UserPresenter implements UserContract.Presenter {
    private UserContract.View mView;
    private UserModel userModel;
    private CompositeSubscription compositeSubscription;

    public UserPresenter() {
        userModel = new UserModel();
    }

    @Override
    public void attachView(UserContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }

    @Override
    public void getUserInfo() {
        mView.showLoading();
        Subscription subscribe = userModel.getUserInfo(mView.getUid())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取用户信息失败:" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onGetInfoSuccess(user);
                        getUserFollowCount();
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getUserFollowCount() {
        Subscription subscribe = userModel.getUserFollowCount(mView.getUid())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mView.onGetUserFollowCountSuccess(integer);
                        getUserFollowerCount();
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getUserFollowerCount() {
        Subscription subscribe = userModel.getUserFollowerCount(mView.getUid())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mView.onGetUserFollowerCountSuccess(integer);
                        getUserNoteCount();
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void getUserNoteCount() {
        Subscription subscribe = userModel.getUserNoteCount(mView.getUid())
                .subscribe(new ResponseSubscriber<Integer>() {
                               @Override
                               public void onError(Throwable e) {
                                   ToastUtil.showShortToastCenter("获取帖子数失败:"+e.getMessage());
                               }

                               @Override
                               public void onNext(Integer integer) {
                                   mView.onGetUserNoteCountSuccess(integer);
                               }
                           });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void getRelationship() {
        Subscription subscribe = userModel.getRelationship(CommonUtil.getCurrentUid(), mView.getUid())
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToastCenter("获取用户关系失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer type) {
                        mView.onGetRelationshipSuccess(type);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void follow() {
        Subscription subscribe = userModel.follow(CommonUtil.getCurrentUid(), mView.getUid())
                .subscribe(new ResponseSubscriber<Object>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToastCenter("关注用户失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        mView.onSuccess("follow");
                        getRelationship();
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void unfollow() {
        Subscription subscribe = userModel.unfollow(CommonUtil.getCurrentUid(), mView.getUid())
                .subscribe(new ResponseSubscriber<Object>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToastCenter("取消关注用户失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        mView.onSuccess("unfollow");
                        getRelationship();
                    }
                });
        compositeSubscription.add(subscribe);
    }


}
