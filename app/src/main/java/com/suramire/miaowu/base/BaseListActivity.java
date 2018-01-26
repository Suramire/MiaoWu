package com.suramire.miaowu.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suramire.miaowu.R;

import butterknife.Bind;

/**
 * Activity基类
 * 处理列表为空的情况
 */

public abstract class BaseListActivity<T extends BasePresenter>  extends BaseActivity<T> implements BaseView {
    @Bind(R.id.listview)
    protected RecyclerView listview;
    @Bind(R.id.tv_empty)
    protected TextView tvEmpty;
    @Bind(R.id.ll_empty)
    protected LinearLayout llEmpty;
    @Bind(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    /**
     * 列表为空时显示的空白页
     * @param emptyHintString 空白提示文本
     */
    public void showEmpty(String emptyHintString){
        listview.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(emptyHintString)){
            tvEmpty.setText(emptyHintString);
        }

    }

    /**
     * 列表有数据时显示数据
     */
    public void showList() {
        listview.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);

    }

}
