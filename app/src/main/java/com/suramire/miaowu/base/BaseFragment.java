package com.suramire.miaowu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Suramire on 2018/1/24.
 */

public abstract class BaseFragment<T extends BasePresenter>  extends Fragment implements BaseView {
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        if(mPresenter!=null){
            mPresenter.attachView(this);
        }
        initView();
    }

    @Override
    public void onDestroy() {
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
}
