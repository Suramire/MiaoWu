package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.HomeModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/29.
 */

public class HomePresenter implements HomeContract.Presenter {
    private final HomeModel homeModel;
    private CompositeSubscription compositeSubscription;
    private HomeContract.View mView;

    public HomePresenter() {
        homeModel = new HomeModel();

    }

    @Override
    public void getData(){
        mView.clearData();
        mView.showLoading();
        Subscription subscribe = homeModel.getData(0, 0)
                .subscribe(new ResponseSubscriber<List<M>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取帖子数据失败:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<M> multis) {
                        mView.cancelLoading();
                        mView.onGetNoteListSuccess(multis);
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void listCats() {
        mView.clearData();
        mView.showLoading();
        Subscription subscribe = homeModel.listCats()
                .subscribe(new ResponseSubscriber<List<Catinfo>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取猫咪列表时出错："+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Catinfo> catinfos) {
                        mView.cancelLoading();
                        mView.onGetCatListSuccess(catinfos);

                    }
                });
        compositeSubscription.add(subscribe);
    }


    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
