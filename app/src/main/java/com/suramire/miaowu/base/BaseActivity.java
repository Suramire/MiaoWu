package com.suramire.miaowu.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Activity基类
 * 封装一些通用方法与属性
 *
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected T mPresenter;
    protected Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(bindLayout());
            ButterKnife.bind(this);
            mContext = this;
            createPresenter();
            if(mPresenter!=null){
                mPresenter.attachView(this);
            }
            initView();
    }



    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }

    /**
     * [绑定布局]
     * @return 布局ID
     */
    public abstract int bindLayout();

    /**
     * [创建控制器]
     */
    public abstract void createPresenter();

    /**
     * [初始化控件]
     */
    public abstract void initView();



    public void startActivity(Class<?> cls){
        startActivity(cls,null);
    }

    public void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 返回图标点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return true;
    }


}
