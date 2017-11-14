package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.model.HomeModel;
import com.suramire.miaowu.util.GsonUtil;

import java.util.List;

/**
 * Created by Suramire on 2017/10/29.
 */

public class HomePresenter implements HomeContract.Presenter {
    private final HomeModel mHomeModel;
    private final Handler mHandler;
    private final HomeContract.View mIHomeView;

    public HomePresenter(HomeContract.View IHomeView) {
        mIHomeView = IHomeView;
        mHomeModel = new HomeModel();
        mHandler = new Handler();
    }

    @Override
    public void getData(){
        mIHomeView.clearData();
        mIHomeView.startLoading();
        mHomeModel.getData(new OnGetResultListener() {
            @Override
            public void onSuccess(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIHomeView.stopLoading();
                        List<Multi> multiBeanList = GsonUtil.jsonToList(object.toString(), Multi.class);
                        mIHomeView.onGetSuccess(multiBeanList);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIHomeView.stopLoading();
                        mIHomeView.onGetFailure(failureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIHomeView.stopLoading();
                        mIHomeView.onGetError(errorMessage);
                    }
                });
            }
        });
    }
}
