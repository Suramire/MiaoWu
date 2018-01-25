package com.suramire.miaowu.presenter;

import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.PublishModel;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.ToastUtil;

import rx.Subscription;
import rx.functions.Action1;
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
    public void publish() {
        //先发送帖子信息（标题、内容、发帖者编号）
        mView.showLoading();
        Subscription subscribe = mPublishModel.publish(mView.getCatinfo(), mView.getNoteTitle(), mView.getNoteContent(),
                mView.getPicturePaths())
                .subscribe(new ResponseSubscriber<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(final Object id) {
                        mView.cancelLoading();
                        mView.onSuccess(null);
                        //再发送帖子图片路径
                        mPublishModel.uploadPicturePath(id, mView.getPicturePaths())
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        //最后上传图片文件
                                        mPublishModel.uploadPicture(mView.getPicturePaths(), 0, mView.getPicturePaths().size(), Integer.parseInt(id.toString()));
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        L.e("发送图片路径出错:" + throwable.getMessage());
                                    }
                                });
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
