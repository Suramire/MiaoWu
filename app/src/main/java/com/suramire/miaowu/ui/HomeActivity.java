package com.suramire.miaowu.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.App;
import com.suramire.miaowu.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/10/16.
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context mContext = App.getApp();
    @Bind(R.id.toolbar_home)
    Toolbar mToolbarHome;
    @Bind(R.id.relist_home)
    RecyclerView mRelistHome;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;
    @Bind(R.id.bottomnavigationview)
    BottomNavigationView mBottomnavigationview;
    @Bind(R.id.nav_view)
    NavigationView mNavView;


    @Override
    protected String getTitleString() {
        return "首页";
    }

    @Override
    protected boolean getDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbarHome);
        View inflateHeaderView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        inflateHeaderView.findViewById(R.id.textView_profile_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
            }
        });
        mNavView.setNavigationItemSelectedListener(this);
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
                helper.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(ContentActivity.class);
                    }
                });
            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==R.id.nav_sentcount){
            startActivity(MainActivity.class);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem search = menu.add(0, 100, 0, "Search");
        search.setIcon(R.drawable.ic_search_black_24dp);
        search.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
}
