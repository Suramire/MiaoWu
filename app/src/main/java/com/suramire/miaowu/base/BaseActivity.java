package com.suramire.miaowu.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Base 封装一些通用方法与属性
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
            App.getInstance().addActivity(this);
            initView();
    }




    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        App.getInstance().removeActivity(this);
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

















    /*为Activity切换提供动画效果*/
    public void startActivity(Class<?> cls){
        startActivity(cls,null);
    }

    public void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }


    /*为Activity退出提供动画效果*/
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return true;
    }


}
