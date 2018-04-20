package com.suramire.miaowu.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.MainContract;
import com.suramire.miaowu.presenter.MainPresenter;
import com.suramire.miaowu.ui.fragment.HomeFragment;
import com.suramire.miaowu.ui.fragment.NotificationFragment;
import com.suramire.miaowu.ui.fragment.UserFragment;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.wiget.LazyViewPager;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/16.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.viewpager)
    LazyViewPager viewpager;
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private boolean requireFresh;
    private HomeFragment homeFragment;
    private int currentPosition;
    private User mUser;
    private int freshPostion;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ApiConfig.LOGINREQUESTCODE && resultCode == ApiConfig.RESULTCODE) {
            //登录成功情况下
            freshPostion = 2;
            requireFresh = true;
            initData();
        }else if(requestCode == ApiConfig.REQUESTCODE_NOTIFICATION && resultCode ==ApiConfig.RESULTCODE_NOTIFICATION){
            //读取通知后
            freshPostion = 1;
            requireFresh = true;
            initData();
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void createPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    public void initView() {
        //设置一次只激活一个fragment
        viewpager.setOffscreenPageLimit(0);
        toolbar.setTitle("首页");
        toolbar.setStyle(MyToolbar.STYLE_RIGHT_AND_TITLE);
        toolbar.setLeftImage(R.drawable.ic_search_black_24dp);
        initData();
    }

    private void initData() {
        mUser = null;
        final ArrayList<Fragment> fragments = new ArrayList<>();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        NotificationFragment notificationFragment = new NotificationFragment();
        notificationFragment.setListener(new NotificationFragment.onNotificationListener() {
            @Override
            public void onChange(int count) {
                bottomNavigationBar.clearAll();
                if(count>0){
                    TextBadgeItem textBadgeItem = new TextBadgeItem().setText(count+"");
                    bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "主页"))
                            .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知").setBadgeItem(textBadgeItem))
                            .addItem(new BottomNavigationItem(R.drawable.ic_person_black, "我的"))
                            .setMode(BottomNavigationBar.MODE_FIXED)
                            .initialise();
                }else{
                    bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "主页"))
                            .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知"))
                            .addItem(new BottomNavigationItem(R.drawable.ic_person_black, "我的"))
                            .setMode(BottomNavigationBar.MODE_FIXED)
                            .initialise();
                }
                bottomNavigationBar.selectTab(currentPosition);
            }
        });
        UserFragment userFragment2 = new UserFragment();
        userFragment2.setListener(new UserFragment.OnUserListener() {
            @Override
            public void onGetUserSuccess(User user) {
                mUser = user;
            }
        });
        fragments.add(homeFragment);
        fragments.add(notificationFragment);
        fragments.add(userFragment2);

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });


        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                viewpager.setCurrentItem(i);
                currentPosition = i;
                switch (i) {
                    case 0:
                        toolbar.setTitle("首页");
                        toolbarTextRight.setVisibility(View.VISIBLE);
                        toolbarTextRight.setText("发帖");
                        toolbarImageLeft.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        toolbar.setTitle("通知中心");
                        toolbarTextRight.setVisibility(View.GONE);
                        toolbarImageLeft.setVisibility(View.GONE);

                        break;
                    case 2:
                        toolbar.setTitle("个人中心");
                        toolbarTextRight.setVisibility(View.VISIBLE);
                        toolbarTextRight.setText("设置");
                        toolbarImageLeft.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int i) {

            }

            @Override
            public void onTabReselected(int i) {

            }
        });

        if (requireFresh) {
            bottomNavigationBar.selectTab(freshPostion);
            requireFresh = false;
        }else {
            bottomNavigationBar.selectTab(0);
        }

        if(CommonUtil.isLogined()){
            mPresenter.getNotificationCount(CommonUtil.getCurrentUid());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {

    }



    @OnClick({R.id.toolbar_image_left, R.id.toolbar_text_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_text_right:
                switch (currentPosition){
                    //个人设置
                    case 2:
                        if(mUser!=null) {
                            Intent intent = new Intent(mContext, ProfileDetailActivity.class);
                            intent.putExtra("uid", CommonUtil.getCurrentUid());
                            startActivityForResult(intent,ApiConfig.LOGINREQUESTCODE);
                        }
                        break;
                    //首页发帖
                    case 0:{
                        if (CommonUtil.isLogined()) {
                            if(CommonUtil.hasContact()){
                                startActivity(NewPublishActivity.class);
                            }else{
                                CommonUtil.snackBar(MainActivity.this, "请先填写联系方式", "填写", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, ProfileDetailActivity.class);
                                        intent.putExtra("uid", CommonUtil.getCurrentUid());
                                        startActivityForResult(intent,ApiConfig.LOGINREQUESTCODE);
                                    }
                                });
                            }

                        } else {
                            CommonUtil.snackBar(MainActivity.this, "您还未登录", "登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(LoginActivity.class);
                                }
                            });
                        }
                    }
                    break;
                }
                break;
            case R.id.toolbar_image_left:
                startActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    public void onGetNotificationCountSuccess(int count) {
        bottomNavigationBar.clearAll();
        if(count>0){

            TextBadgeItem textBadgeItem = new TextBadgeItem().setText(count+"");
            bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "主页"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知").setBadgeItem(textBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_person_black, "我的"))
                    .setMode(BottomNavigationBar.MODE_FIXED)
                    .initialise();
        }else{
            bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "主页"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_person_black, "我的"))
                    .setMode(BottomNavigationBar.MODE_FIXED)
                    .initialise();
        }

    }


//
// TODO: 2017/10/29 内存泄漏处理
//
}
