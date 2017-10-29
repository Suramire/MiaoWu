package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.model.ProfileModel;

/**
 * Created by Suramire on 2017/10/25.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileModel mProfileModel;
    private final Handler mHandler;
    private final ProfileContract.View mIProfileView;

    public ProfilePresenter(ProfileContract.View IProfileView) {
        mIProfileView = IProfileView;
        mProfileModel = new ProfileModel();
        mHandler = new Handler();
    }

    @Override
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
