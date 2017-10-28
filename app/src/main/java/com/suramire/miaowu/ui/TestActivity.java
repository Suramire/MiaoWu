package com.suramire.miaowu.ui;

import android.view.View;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

/**
 * Created by Suramire on 2017/10/17.
 */

public class TestActivity extends BaseActivity {



    @Override
    protected String getTitleString() {
        return "评论详情";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_profile_test;
    }

    @Override
    public void initView(View view) {

    }

}
