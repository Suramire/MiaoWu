package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.OnGetResultListener;

import java.util.List;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface PublishContract {
    interface Model {
        void publish(String title, String content, List<String> pictues, OnGetResultListener listener);

        void uploadPicture(Object object, List<String> pictues, final OnGetResultListener listener);
    }

    interface View {
        void startPublishing();

        void stopPublishing();

        void onPublishSuccess();

        void onPublishFailure(String failureMessage);

        void onPublishError(String errorMessage);

        String getNoteTitle();

        String getNoteContent();

        //帖子配图的路径
        List<String> getPicturePaths();
    }

    interface Presenter {
        void publish();
    }
}