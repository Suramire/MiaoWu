package com.suramire.miaowu.base;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Suramire on 2018/1/24.
 */

public abstract class BaseDialogFragment<T extends BasePresenter>  extends DialogFragment implements BaseView {
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
     * [创建控制器]
     */
    public abstract void createPresenter();

    /**
     * [初始化控件]
     */
    public abstract void initView();
}
