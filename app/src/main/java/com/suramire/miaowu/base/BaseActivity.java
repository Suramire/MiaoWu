package com.suramire.miaowu.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.suramire.miaowu.R;
import com.suramire.miaowu.wiget.MyToolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity基类
 * 封装一些通用方法与属性
 *
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    @Bind(R.id.toolbar)
    @Nullable
    MyToolbar toolbar;

    protected T mPresenter;
    protected Activity mContext;
    private boolean closable = true;//是否直接关闭当前Activity
    protected ProgressDialog progressDialog;

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createPresenter();
        if(mPresenter!=null){
            mPresenter.attachView(this);
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        initView();


    }


    /**
     * 设置标题文字
     * @param titleString 标题文本
     */
    protected void setTitle(String titleString){
        if(null != toolbar){
            toolbar.setTitle(titleString);
        }
    }

    /**
     * 设置右边操作选项文字
     * @param optionString 选项文本
     */
    protected void setRightText(String optionString){
        if(null != toolbar){
            toolbar.setRightText(optionString);
        }
    }

    /**
     * 设置左边图片
     * @param resId 图片资源id
     */
    protected void setLeftImage(int resId){
        if(null != toolbar){
            toolbar.setLeftImage(resId);
        }
    }

    /**
     * 设置toolbar样式
     * @param style 样式编号
     */
    protected void setStyle(int style){
        if (null != toolbar){
            toolbar.setStyle(style);
        }
    }

    //返回箭头点击事件
    @Nullable
    @OnClick(R.id.toolbar_image_left)
    public void onViewClicked(){
        if(isClosable()){
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void cancelLoading() {
        progressDialog.dismiss();
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
