package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface ProfileContract {
    interface Model {
        void getProfile(int id, OnGetResultListener listener);
    }

    interface View {
        void showLoading();

        void cancelLoading();

        void onGetProfileSuccess(Object object);

        void onGetProfileFaiure(String failureMessage);

        void onGetProfileError(String errorMessage);

        int getUid();
    }

    interface Presenter {
        void getProfile();
    }
}
