package com.suramire.miaowu.presenter;

import android.support.v7.view.menu.MenuView;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.CatModel;
import com.suramire.miaowu.util.OnResultListener;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CatPresenter implements CatContract.Presenter {

    private final CatModel catModel;
    private CatContract.View mView;
    private CompositeSubscription compositeSubscription;

    public CatPresenter() {
        catModel = new CatModel();
        catModel.setListener(new OnResultListener() {
            @Override
            public void onError(String errorMessage) {
                ToastUtil.showLongToast(errorMessage);
            }

            @Override
            public void onFailed(String failureMessage) {

            }

            @Override
            public void onSuccess(Object object) {
                mView.cancelLoading();
                mView.onUploadCatPicturesSuccess();
            }
        });
    }

    @Override
    public void addCat() {
        mView.showLoading();
        Subscription subscribe = catModel.addCat(mView.getCatInfo())
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("添加猫咪信息时出错:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer cid) {
                        mView.cancelLoading();
                        mView.onAddCatSuccess(cid);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void getCat() {
        mView.showLoading();
        Subscription subscribe = catModel.getCat(mView.getCatId())
                .subscribe(new ResponseSubscriber<Catinfo>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取猫咪信息时出错：" + e.getMessage());
                    }

                    @Override
                    public void onNext(Catinfo catinfo) {
                        mView.cancelLoading();
                        mView.onSuccess(catinfo);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void uploadPictuePaths() {
        mView.showLoading();
        catModel.uploadPicturePaths(mView.getCatId(),mView.getStringPaths())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("保存配图文件名时出错："+e.getMessage());
                    }

                    @Override
                    public void onNext(Void o) {
                        uploadPicture();
                    }
                });
    }


    @Override
    public void uploadPicture() {
//        mView.showLoading();
        catModel.uploadPicture(mView.getStringPaths(),mView.getCatId());

    }

    @Override
    public void getAllPictures() {
        mView.showLoading();
        catModel.getAllPictures(mView.getCatId())
                .subscribe(new ResponseSubscriber<List<String>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取猫咪配图时出错："+e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mView.cancelLoading();
                        mView.onGetAllPicturesSuccess(strings);
                    }
                });
    }

    @Override
    public void applyCat() {
        mView.showLoading();
        Subscription subscribe = catModel.applyCat(mView.getCatId(), mView.getUid())
                .subscribe(new ResponseSubscriber<Void>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("申请失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.cancelLoading();
                        mView.onApplyCatSuccess();

                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void listAppliedCat() {

        mView.showLoading();
        Subscription subscribe = catModel.listAppliedCat()
                .subscribe(new ResponseSubscriber<List<M>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("获取待审核列表出错："+e.getMessage());
                    }

                    @Override
                    public void onNext(List<M> catinfo) {
                        mView.cancelLoading();
                        mView.onListAppliedCatSuccess(catinfo);
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void attachView(CatContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();
    }
}
