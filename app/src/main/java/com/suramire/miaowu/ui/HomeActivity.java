package com.suramire.miaowu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.App;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Suramire on 2017/10/16.
 */

public class HomeActivity extends AppCompatActivity {
    Context mContext = App.getApp();
    @Bind(R.id.toolbar_home)
    Toolbar mToolbarHome;
    @Bind(R.id.relist_home)
    RecyclerView mRelistHome;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarHome);
        initView();
    }

    private void initView() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbarHome, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add("test");
        }
        final int[] images = new int[]{R.drawable.cat, R.drawable.cat1, R.drawable.cat2, R.drawable.cat3};
        mRelistHome.setLayoutManager(new LinearLayoutManager(mContext));
        mRelistHome.setAdapter(new CommonRecyclerAdapter(mContext, R.layout.item_list, list) {

            @Override
            public void onUpdate(BaseAdapterHelper helper, Object item, int position) {
                Picasso.with(App.getApp()).load(images[position]).into((ImageView) helper.getView(R.id.imageView3));
            }
        });
    }
}
