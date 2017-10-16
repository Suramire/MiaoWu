package com.suramire.miaowu.base;

import com.mob.MobApplication;

/**
 * Created by Suramire on 2017/10/15.
 */

public class App extends MobApplication {

    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static App getApp() {
        return mApp;
    }
}
