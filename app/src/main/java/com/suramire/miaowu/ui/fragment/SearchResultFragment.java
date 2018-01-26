package com.suramire.miaowu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.presenter.HomePresenter;
import com.suramire.miaowu.util.FileUtil;

/**
 * Created by Suramire on 2018/1/26.
 */

public class SearchResultFragment extends BaseListFragment<HomePresenter> {


    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void initView() {
        if (false) {
            showEmpty(null);
            swipeRefreshLayout.setEnabled(false);
        } else {
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<String>(mContext, R.layout.item_search_note, FileUtil.getStrings(5)) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, String item, int position) {

                }
            });
        }


    }


}
