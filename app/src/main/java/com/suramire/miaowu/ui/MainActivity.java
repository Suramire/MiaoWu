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
import com.suramire.miaowu.ui.fragment.HomeFragment;
import com.suramire.miaowu.ui.fragment.NotificationFragment;
import com.suramire.miaowu.ui.fragment.PersonFragment;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;
import com.suramire.miaowu.wiget.MyViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/16.
 */

public class MainActivity extends BaseActivity {
    private static final int REQUESTCODE = 0x100;
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.viewpager)
    MyViewPager viewpager;
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private boolean requireFresh;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ApiConfig.LOGINREQUESTCODE && resultCode == ApiConfig.RESULTCODE) {
            //登录成功情况下
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

    }

    @Override
    public void initView() {
        toolbar.setTitle("首页");
//        toolbar.setStyle(MyToolbar.STYLE_RIGHT_AND_TITLE);
//        toolbar.setRightOnclickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonUtil.isLogined()) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("请选择帖子类型");
//                    final String[] items = {"领养", "求领养"};
//                    builder.setItems(items, new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(MainActivity.this, NewPublishActivity.class);
//                            switch (which) {
//                                case 0: {
//                                    intent.putExtra("needcat", false);
//                                }
//                                break;
//                                case 1: {
//                                    intent.putExtra("needcat", true);
//                                }
//                                break;
//                            }
//                            startActivity(intent);
//
//                        }
//                    });
//                    builder.setCancelable(false).show();
//                } else {
//                    CommonUtil.snackBar(MainActivity.this, "您还未登录", "登录", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(LoginActivity.class);
//                        }
//                    });
//                }
//            }
//        });


        TextBadgeItem textBadgeItem = new TextBadgeItem().setText("5");

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "通知").setBadgeItem(textBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_black, "我的"))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .initialise();

        initData();

    }

    private void initData() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (notificationFragment == null) {
            notificationFragment = new NotificationFragment();
        }
        PersonFragment personFragment2 = new PersonFragment();
        fragments.add(homeFragment);
        fragments.add(notificationFragment);
        fragments.add(personFragment2);

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
                        toolbarTextRight.setVisibility(View.GONE);
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
//            viewpager.setCurrentItem(3);
            bottomNavigationBar.selectTab(2);
            requireFresh = false;
        }else {
            bottomNavigationBar.selectTab(0);
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
                ToastUtil.showShortToastCenter("响应发帖操作");
                break;
            case R.id.toolbar_image_left:
                startActivity(SearchActivity.class);
                break;
        }
    }


//
// TODO: 2017/10/29 内存泄漏处理 activity已销毁应停止后台耗时操作
//
}
