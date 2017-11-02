package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/11/2.
 */

public interface CommonContract {
    interface Model {
        void postSomething(OnGetResultListener listener,Object... objects);

        void getSomething(OnGetResultListener listener,Object... objects);
    }

    interface View {
        void showLoading();

        void cancelLoading();

        void onSuccess(Object object);

        void onFailure(String failureMessage);

        void onError(String errorMessage);
    }

    interface Presenter {
        void postSomething();

        void getSomething();
    }
}
