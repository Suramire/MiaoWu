package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.model.IProfileModel;
import com.suramire.miaowu.model.ProfileModel;
import com.suramire.miaowu.view.IProfileView;

/**
 * Created by Suramire on 2017/10/25.
 */

public class ProfilePresenter {
    private final IProfileModel mProfileModel;
    private final Handler mHandler;
    private IProfileView mIProfileView;

    public ProfilePresenter(IProfileView IProfileView) {
        mIProfileView = IProfileView;
        mProfileModel = new ProfileModel();
        mHandler = new Handler();
    }

    public void getProfile(){
        mIProfileView.showLoading();
        mProfileModel.getProfile(mIProfileView.getUid(), new OnGetResultListener() {
            @Override
            public void onSuccess(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIProfileView.cancelLoading();
                        mIProfileView.onGetProfileSuccess(object);
                    }
                });
            }

            @Override
            public void onFailure(String failureMessage) {
                mIProfileView.cancelLoading();
                mIProfileView.onGetProfileFaiure(failureMessage);
            }

            @Override
            public void onError(String errorMessage) {
                mIProfileView.cancelLoading();
                mIProfileView.onGetProfileError(errorMessage);
            }
        });
    }
}
