package com.suramire.miaowu.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
 * 首页
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
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
            //登录成功情况下 刷新个人中心
            freshPostion = 2;
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
        setTitle("首页");
        setClosable(false);//需要重写返回按钮事件
        setStyle(MyToolbar.STYLE_RIGHT_AND_TITLE);
        setLeftImage(R.drawable.ic_search_black_24dp);
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
                setBottomBar(count);
            }
        });
        UserFragment userFragment2 = new UserFragment();
        userFragment2.setListener(new UserFragment.OnUserListener() {
            @Override
            public void onGetUserSuccess(User user) {
                mUser = user;
            }

            @Override
            public void onGetNoteCountSuccess() {
                //在成功获取用户发帖数之后才获取未读通知数
                if(CommonUtil.isLogined()){
                    mPresenter.getNotificationCount(CommonUtil.getCurrentUid());
                }
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
        setBottomBar(0);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                viewpager.setCurrentItem(i);
                currentPosition = i;
                switch (i) {
                    case 0:
                        setTitle("首页");
                        toolbarTextRight.setVisibility(View.VISIBLE);
                        setRightText("发帖");
                        toolbarImageLeft.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setTitle("通知中心");
                        toolbarTextRight.setVisibility(View.GONE);
                        toolbarImageLeft.setVisibility(View.GONE);

                        break;
                    case 2:
                        setTitle("个人中心");
                        toolbarTextRight.setVisibility(View.VISIBLE);
                        setRightText("设置");
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
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    startActivityForResult(intent,ApiConfig.LOGINREQUESTCODE);
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
        setBottomBar(count);
    }

    /**
     * 设置底部导航条
     * @param count 未读通知数
     */
    private void setBottomBar(int count){
        bottomNavigationBar.clearAll();
        BottomNavigationBar home = bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "主页"));
        BottomNavigationBar notification;
        if(count>0){
            TextBadgeItem textBadgeItem = new TextBadgeItem().setText(count+"");
            notification = home.addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知").setBadgeItem(textBadgeItem));
        }else{
            notification = home.addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知"));
        }
        notification.addItem(new BottomNavigationItem(R.drawable.ic_person_black, "我的"))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .initialise();
        bottomNavigationBar.selectTab(currentPosition);
    }

}
