package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.model.HomeModel;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Suramire on 2017/10/29.
 */

public class HomePresenter implements HomeContract.Presenter {
    private final HomeModel mHomeModel;
    private HomeContract.View mView;

    public HomePresenter() {
        mHomeModel = new HomeModel();
    }

    @Override
    public void getData(){
        mView.clearData();
        mView.showLoading();
        mHomeModel.getData(0,0)
                .subscribe(new Action1<List<Multi>>() {
                    @Override
                    public void call(List<Multi> multis) {
                        mView.cancelLoading();
                        mView.onSuccess(multis);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }
                });
    }

    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
