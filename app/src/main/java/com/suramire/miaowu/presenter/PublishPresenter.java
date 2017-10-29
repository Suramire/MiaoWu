package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.model.PublishModel;
import com.suramire.miaowu.view.IPublishView;

/**
 * Created by Suramire on 2017/10/29.
 */

public class PublishPresenter {
    private final PublishModel mPublishModel;
    private final IPublishView mIPublishView;
    private final Handler mHandler;

    // TODO: 2017/10/29 资源回收？
    public PublishPresenter(IPublishView IPublishView) {
        mIPublishView = IPublishView;
        mPublishModel = new PublishModel();
        mHandler = new Handler();
    }

    public void publish() {
        mIPublishView.startPublishing();
        mPublishModel.publish(mIPublishView.getNoteTitle(), mIPublishView.getNoteContent(),
                mIPublishView.getPicturePaths(), new OnGetResultListener() {
                    @Override
                    public void onSuccess(Object object) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mIPublishView.stopPublishing();
                                mIPublishView.onPublishSuccess();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final String failureMessage) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mIPublishView.stopPublishing();
                                mIPublishView.onPublishFailure(failureMessage);
                            }
                        });
                    }

                    @Override
                    public void onError(final String errorMessage) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mIPublishView.stopPublishing();
                                mIPublishView.onPublishError(errorMessage);
                            }
                        });
                    }
                });
    }
}
