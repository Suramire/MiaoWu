package com.suramire.miaowu.presenter;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.http.base.ResponseSubscriber;
import com.suramire.miaowu.model.ProfileModel;
import com.suramire.miaowu.util.ToastUtil;

/**
 * Created by Suramire on 2017/10/25.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileModel mProfileModel;
    private ProfileContract.View mView;

    public ProfilePresenter() {
        mProfileModel = new ProfileModel();
    }

    @Override
    public void getProfile(){
        mView.showLoading();
        mProfileModel.getProfile(mView.getUid())
                .subscribe(new ResponseSubscriber<User>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.cancelLoading();
                        ToastUtil.showShortToastCenter(throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.cancelLoading();
                        mView.onSuccess(user);
                    }
                });
    }

    @Override
    public void attachView(ProfileContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
