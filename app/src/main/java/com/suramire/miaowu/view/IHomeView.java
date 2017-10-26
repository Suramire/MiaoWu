package com.suramire.miaowu.view;

/**
 * Created by Suramire on 2017/10/24.
 */

public interface IHomeView {
    void startLoading();

    void endLoading();

    void onGetSuccess();

    void onGetFailure();
}
