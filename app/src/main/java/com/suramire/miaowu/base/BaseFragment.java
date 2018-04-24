package com.suramire.miaowu.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 */

public abstract class BaseFragment<T extends BasePresenter>  extends Fragment implements BaseView {
    protected T mPresenter;
    protected Context mContext;
    private View view;
    protected ProgressDialog progressDialog;

    public void setRequireFresh(boolean requireFresh) {
        this.requireFresh = requireFresh;
    }

    //Fragment刷新标志位 false=保留上次销毁的视图
    private boolean requireFresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        if(requireFresh){
            view = inflater.inflate(bindLayout(), null);
            ButterKnife.bind(this, view);
            createPresenter();
            if(mPresenter!=null){
                mPresenter.attachView(this);
            }
            initView();
        }else{
            if(view ==null){
                view = inflater.inflate(bindLayout(), null);
                ButterKnife.bind(this, view);
                createPresenter();
                if(mPresenter!=null){
                    mPresenter.attachView(this);
                }
                initView();
            }
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(requireFresh){
            if(mPresenter!=null){
                mPresenter.detachView();
            }
            ButterKnife.unbind(this);
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

    public void startActivityForResult(Class<?> cls,int requestCode){
        Intent intent = new Intent(mContext, cls);
        getActivity().startActivityForResult(intent,requestCode);
    }



    public void  startActivity(Class<?> cls){
        startActivity(cls,null);
    }

    public void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
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
