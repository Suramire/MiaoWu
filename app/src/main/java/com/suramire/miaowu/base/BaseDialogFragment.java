package com.suramire.miaowu.base;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Suramire on 2018/1/24.
 */

public abstract class BaseDialogFragment<T extends BasePresenter>  extends DialogFragment implements BaseView {
    protected T mPresenter;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(bindLayout(), null);
        ButterKnife.bind(this, view);
        createPresenter();
        if(mPresenter!=null){
            mPresenter.attachView(this);
        }
        initView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        ButterKnife.unbind(this);
    }

    /**
     * [创建控制器]
     */
    public abstract void createPresenter();

    /**
     * [初始化控件]
     */
    public abstract void initView();

    /**
     * [绑定布局]
     * @return 布局ID
     */
    public abstract int bindLayout();



}
