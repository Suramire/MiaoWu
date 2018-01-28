package com.suramire.miaowu.presenter;

import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.PublishModel;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Suramire on 2017/10/29.
 */

public class PublishPresenter implements PublishContract.Presenter {
    private final PublishModel mPublishModel;
    private PublishContract.View mView;
    private CompositeSubscription compositeSubscription;

    public PublishPresenter() {
        mPublishModel = new PublishModel();
    }


    @Override
    public void publishNote(final int type, int catId) {
        Subscription subscribe = mPublishModel.publicNoteInfo(mView.getNoteInfo(), type, catId)
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("发布帖子信息失败:"+throwable.getMessage());

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if(mView.getPicturePaths()!=null && mView.getPicturePaths().size()>0 ){
                            publishPicturePaths(integer);
                        }else {
                            mView.onSuccess(null);
                        }
                    }
                });

        compositeSubscription.add(subscribe);
    }

    @Override
    public void publishCat() {
        mView.showLoading();
        Subscription subscribe = mPublishModel.publishCatInfo(mView.getCatinfo())
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShortToastCenter("发布猫咪信息失败:" + throwable.getMessage());
                        publishNote(2, 0);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        publishNote(2, integer);
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void publishPicturePaths(final int nid) {
        Subscription subscribe = mPublishModel.uploadPicturePath(nid, mView.getPicturePaths())
                .subscribe(new ResponseSubscriber<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter("上传图片路径失败:" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(Object object) {
                        mView.cancelLoading();
                        mView.onSuccess(null);
                        //上传图片
                        mPublishModel.uploadPicture(mView.getPicturePaths(), 0, mView.getPicturePaths().size(), nid);
                    }
                });
        compositeSubscription.add(subscribe);

    }

    @Override
    public void attachView(PublishContract.View view) {
        mView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mView = null;
        compositeSubscription.unsubscribe();

    }
}
