package com.suramire.miaowu.view;

import java.util.List;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface IPublishView {
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
