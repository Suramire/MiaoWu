package com.suramire.miaowu.model;

import com.suramire.miaowu.base.OnGetResultListener;

/**
 * Created by Suramire on 2017/10/25.
 */

public interface IProfileModel {
    void getProfile(int uid , OnGetResultListener listener);
}
