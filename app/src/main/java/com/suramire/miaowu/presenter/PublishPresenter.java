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
    public void publishNote() {
        mView.showLoading();//开始操作前 显示界面的加载动画
        //subcribe 被观察者 ResponseSubscriber匿名内部类 观察者
        Subscription subscribe = mPublishModel.
                publishNoteInfo(mView.getNoteInfo())
                .subscribe(new ResponseSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable throwable) {
                        //回调 出现异常时
                        mView.cancelLoading();//结束界面里的加载动画
                        ToastUtil.
                        showShortToastCenter("发布帖子信息失败:"+
                                throwable.getMessage());
                    }
                    @Override
                    public void onNext(Integer integer) {
                        //回调 正常执行时
                        //帖子有配图的情况 发送配图文件至服务器
                        if(mView.getPicturePaths()!=null
                                && mView.getPicturePaths().size()>0 ){
                            publishPicturePaths(integer);
                        }else {
                            //无配图情况直接调用界面中的成功方法
                            mView.onSuccess(null);
                        }
                    }
                });
        compositeSubscription.add(subscribe);/*为了方便在本Presenter生命
                                        周期结束时取消订阅Subcription对象*/
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
