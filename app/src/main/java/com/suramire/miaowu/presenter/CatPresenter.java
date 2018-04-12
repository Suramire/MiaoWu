package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.CatModel;
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
    public void listCats() {
        mView.showLoading();
        Subscription subscribe = catModel.listCats()
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
    public void uploadPicture() {
//        mView.showLoading();
        catModel.uploadPicture(mView.getStringPaths(),mView.getCatId());

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
