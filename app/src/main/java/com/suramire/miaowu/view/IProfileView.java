package com.suramire.miaowu.view;

/**
 * Created by Suramire on 2017/10/25.
 */

public interface IProfileView {
    void showLoading();

    void cancelLoading();

    void onGetProfileSuccess(Object object);

    void onGetProfileFaiure(String failureMessage);

    void onGetProfileError(String errorMessage);

    int getUid();
}
