package com.suramire.miaowu.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.suramire.miaowu.R;

import butterknife.ButterKnife;

/**
 * Created by Suramire on 2017/10/17.
 * Base 封装一些通用方法与属性
 */

public abstract class BaseActivity extends AppCompatActivity {


    private View mContextView = null;
    private boolean mDisplayHomeAsUpEnabled = true;


    public View getContextView() {
        return mContextView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
            setContentView(mContextView);
            ButterKnife.bind(this);
            initView(mContextView);
            setActionBarTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle(getTitleString());
            actionBar.setDisplayHomeAsUpEnabled(getDisplayHomeAsUpEnabled());

        }
    }

    protected abstract String getTitleString();

    protected boolean getDisplayHomeAsUpEnabled(){
        return mDisplayHomeAsUpEnabled;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    /*为Activity切换提供动画效果*/
    public void startActivity(Class<?> cls){
        startActivity(new Intent(App.getApp(),cls));
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
    /*为Activity退出提供动画效果*/
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return true;
    }

    /**
     * [沉浸状态栏]
     */
    public void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * [绑定布局]
     *
     * @return 布局ID
     */
    public abstract int bindLayout();


    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(View view);

}
