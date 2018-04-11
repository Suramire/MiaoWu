package com.suramire.miaowu.base;

import android.app.Activity;

import com.mob.MobApplication;

import java.util.HashSet;

/**
 * 应用基类
 */

public class App extends MobApplication {

    public static App instance;
    public  synchronized static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
