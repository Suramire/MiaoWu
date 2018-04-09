package com.suramire.miaowu.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.SearchContract;
import com.suramire.miaowu.presenter.SearchPresenter;
import com.suramire.miaowu.ui.fragment.SearchResultFragment;
import com.suramire.miaowu.util.L;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Suramire on 2018/1/26.
 */

public class SearchActivity extends BaseSwipeActivity<SearchPresenter> implements SearchContract.View {
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private String mQuery;
    private List<Note> mNotes;

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
        return R.layout.activity_search;
    }

    @Override
    public void createPresenter() {
        mPresenter = new SearchPresenter();
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView.setIconified(false);
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                setupResult(query);
                mQuery = query;
                mPresenter.searchNote();
                L.e("onQueryTextSubmit");


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                L.e("onQueryTextChange");
                return false;
            }
        });


    }

    private void setupResult(List<Note> notes,List<User> users) {
//        final String[] tabTitles = {"帖子","用户"};
        final List<String> titles = new ArrayList<>();
        final List<Fragment> fragments = new ArrayList<>();


            Bundle bundle = new Bundle();
            bundle.putInt("index",0);
            bundle.putSerializable("notes", (Serializable) notes);
            SearchResultFragment searchResultFragment = new SearchResultFragment();
            searchResultFragment.setArguments(bundle);
            fragments.add(searchResultFragment);
            titles.add("帖子");


            Bundle bundle2 = new Bundle();
            bundle2.putInt("index",1);
            bundle2.putSerializable("users", (Serializable) users);
            SearchResultFragment searchResultFragment2 = new SearchResultFragment();
            searchResultFragment2.setArguments(bundle2);
            fragments.add(searchResultFragment2);
            titles.add("用户");



        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        tablayout.setupWithViewPager(viewpager);

    }


    @Override
    public void onUserSuccess(List<User> users) {
        setupResult(mNotes, users);
        L.e("users.size:" + users.size());
    }

    @Override
    public void onNoteSuccess(List<Note> notes) {
        mNotes = notes;
        L.e("notes.size:" + notes.size());
    }

    @Override
    public String getQuery() {
        return mQuery;
    }
}
