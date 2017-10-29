package com.suramire.miaowu.view;

/**
 * Created by Suramire on 2017/10/24.
 */

public interface IHomeView {
    void startLoading();

    void stopLoading();

    void clearData();

    void onGetSuccess(Object object);

    void onGetFailure(String failMessage);

    void onGetError(String errorMessage);
}
