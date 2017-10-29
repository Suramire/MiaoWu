package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface HomeContract {
    interface Model {
        void getData(OnGetResultListener listener);
    }

    interface View {

        void startLoading();

        void stopLoading();

        void clearData();

        void onGetSuccess(Object object);

        void onGetFailure(String failMessage);

        void onGetError(String errorMessage);
    }

    interface Presenter {
        void getData();
    }
}
