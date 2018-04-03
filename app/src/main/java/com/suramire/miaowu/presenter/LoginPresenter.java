package com.suramire.miaowu.presenter;

import android.text.TextUtils;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.LoginModel;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginModel mLoginModel;
    private LoginContract.View mView;
    private CompositeSubscription compositeSubscription;

    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String name, String password) {
        mView.showLoading();
        String sName = name;
        String sPassword = password;
        //若传入用户名密码则从页面上的输入框获取
        //两个参数为空表示主页面的自动登录
        if (TextUtils.isEmpty(sName) && TextUtils.isEmpty(sPassword)) {
            sName = mView.getUserName();
            sPassword = mView.getPassword();
        }
        if(!TextUtils.isEmpty(sName) && !TextUtils.isEmpty(sPassword)){
            Subscription subscribe = mLoginModel.doLogin(sName, sPassword)
                    .subscribe(new ResponseSubscriber<User>() {
                                   @Override
                                   public void onError(Throwable e) {
                                       mView.cancelLoading();
                                       ToastUtil.showShortToastCenter(e.getMessage());
                                   }
                                   @Override
                                   public void onNext(User user) {
                                       mView.cancelLoading();
                                       mView.onSuccess(user);
                                   }
                               }
                    );
            compositeSubscription.add(subscribe);
        }else{
            ToastUtil.showShortToastCenter("用户名和密码不能为空！");
        }
    }

    @Override
    public void getUserInfo(int uid) {
        mView.showLoading();
        Subscription subscribe = mLoginModel.getUserInfo(uid)
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        SPUtils.put("uid", 0);
                        ToastUtil.showShortToastCenter("获取用户信息失败:" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onGetInfoSuccess(user);
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void attachView(LoginContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
