package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.UserModel;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
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
    public void getUserInfo(final int flag) {
        mView.showLoading();
        Subscription subscribe = userModel.getUserInfo(mView.getUid())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("获取用户信息失败:" + throwable.getMessage());
                        if(flag==1){
//                            在首页获取用户信息失败时 清空登录信息
                            CommonUtil.loginOut();
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        mView.onGetInfoSuccess(user);
                        getUserFollowCount();
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getUserFollowCount() {
        Subscription subscribe = userModel.getUserFollowCount(mView.getUid())
                .subscribe(
                        new ResponseSubscriber<Integer>() {
                            @Override
                            public void onError(Throwable e) {
                                mView.cancelLoading();
                                ToastUtil.showLongToastCenter("获取关注数出错："+e.getMessage());
                            }

                            @Override
                            public void onNext(Integer integer) {
                                mView.onGetUserFollowCountSuccess(integer);
                                getUserFollowerCount();
                            }
                        }
        );
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getUserFollowerCount() {
        Subscription subscribe = userModel.getUserFollowerCount(mView.getUid())
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showLongToastCenter("获取粉丝数出错："+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
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
                                   mView.cancelLoading();
                                   ToastUtil.showLongToastCenter("获取帖子数失败:"+e.getMessage());
                               }

                               @Override
                               public void onNext(Integer integer) {
                                   mView.cancelLoading();
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
                        ToastUtil.showLongToastCenter("获取用户关系失败:"+e.getMessage());
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
                        ToastUtil.showLongToastCenter("关注用户失败:"+e.getMessage());
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
                        ToastUtil.showLongToastCenter("取消关注用户失败:"+e.getMessage());
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
