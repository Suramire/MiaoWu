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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.App;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.presenter.LoginPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.Constant;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.view.ILoginView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/10/16.
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ILoginView {
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
    private LoginPresenter mLoginPresenter;
    private ImageView mImageView;
    private TextView mTextView;


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
        mLoginPresenter = new LoginPresenter(this);
        View inflateHeaderView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        mTextView = inflateHeaderView.findViewById(R.id.textView_profile_username);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CommonUtil.isLogined()){
                    startActivity(LoginActivity.class);
                }

            }
        });
        mImageView = inflateHeaderView.findViewById(R.id.imageView);
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

        //进入程序时 尝试自动登录
        if ((int) SPUtils.get("uid", 0) != 0 && (int) SPUtils.get("autologin", 0) == 1) {
            //之前登录过 自动登录
            String nickname = (String) SPUtils.get("nickname", "");
            String password = (String) SPUtils.get("password", "");
            mLoginPresenter.login(nickname, password);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_sentcount) {
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


    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void onSuccess(String resultString) {
        L.e("登录成功");
        // TODO: 2017/10/22 这里进行信息更新操作
        User user = (User) GsonUtil.jsonToObject(resultString, User.class);
        String icon = user.getIcon();
        //头像设置
        if(!TextUtils.isEmpty(icon)){
            L.e(Constant.BASEURL + "upload/" + icon);
            Picasso.with(this).load(Constant.BASEURL + "upload/" + icon).into(mImageView);
        }else{
            //默认头像
        }

        //文本标签设置
        mTextView.setText(user.getNickname());

    }

    @Override
    public void onFailure(String fialureMessage) {
        L.e("登录失败");
    }

    @Override
    public void onError(String errorMessage) {
        L.e("登录出错");
    }

}
