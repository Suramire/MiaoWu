package com.suramire.miaowu.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.NotePhoto;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.presenter.HomePresenter;
import com.suramire.miaowu.presenter.LoginPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.SPUtils;

import java.util.List;

import butterknife.Bind;

import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/10/16.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements NavigationView.OnNavigationItemSelectedListener, LoginContract.View, HomeContract.View {
    private static final int REQUESTCODE = 0x100;
    Context mContext = this;
    @Bind(R.id.toolbar)
    Toolbar mToolbarHome;
    @Bind(R.id.relist_home)
    RecyclerView mRelistHome;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;
    @Bind(R.id.bottomnavigationview)
    BottomNavigationView mBottomnavigationview;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LoginPresenter mLoginPresenter;
    private ImageView mImageView;
    private TextView mTextView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView mImageView7;






    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void createPresenter() {
        mPresenter =new HomePresenter();
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbarHome);
        setTitle("首页");

        View inflateHeaderView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        mTextView = inflateHeaderView.findViewById(R.id.textView_profile_username);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
            }
        });
        mImageView = inflateHeaderView.findViewById(R.id.imageView);
        mImageView7 = inflateHeaderView.findViewById(R.id.imageView7);
        mImageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.callOnClick();
            }
        });
        mNavView.setNavigationItemSelectedListener(this);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbarHome, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
//        initData();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData();
            }
        });
    }





    private void doLoginout() {
        onLoginSuccess(null);
    }

    private void doLogin() {
        //进入程序时 尝试自动登录
        if ((int) SPUtils.get("uid", 0) != 0 && (int) SPUtils.get("autologin", 0) == 1) {
            //之前登录过 自动登录
            String nickname = (String) SPUtils.get("nickname", "");
            String password = (String) SPUtils.get("password", "");
            mLoginPresenter.login(nickname, password);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == ProfileActivity.SUCCESS) {
            //在个人中心点击了注销
            doLoginout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        doLogin();
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
        menu.add(0, 100, 0, "Search")
        .setIcon(R.drawable.ic_search_black_24dp)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 101, 1, "Publish")
                .setIcon(R.drawable.ic_create_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100: break;
            case 101:{
                if(CommonUtil.isLogined()){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                    builder.setTitle("请选择帖子类型");
                    final String[] items = {"领养","求领养"};
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(HomeActivity.this, NewPublishActivity.class);
                            switch (which){
                                case 0:{
                                    intent.putExtra("needcat",false);
                                }break;
                                case 1:{
                                    intent.putExtra("needcat",true);
                                }break;
                            }
                            startActivity(intent);

                        }
                    });
                    builder.setCancelable(false).show();
                }else{
                    CommonUtil.snackBar(this,"您还未登录","登录",new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(LoginActivity.class);
                                }
                            });
                }

            }break;
        }
        return true;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void cancelLoading() {
// TODO: 2017/10/29 内存泄漏处理 activity已销毁应停止后台耗时操作
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccess(Object object) {
        final List<Multi> notes = (List<Multi>) object;
        if(notes.size()>0){

            mRelistHome.setLayoutManager(new LinearLayoutManager(mContext));
            mRelistHome.setAdapter(new CommonRecyclerAdapter<Multi>(mContext, R.layout.item_home, notes) {

                @Override
                public void onUpdate(final BaseAdapterHelper helper, final Multi item, int position) {
                    final Note note = item.getmNote();
                    NotePhoto notePhoto = item.getmNotePhoto();
                    User user = item.getmUser();
                    Picasso.with(mContext)
                            .load(BASNOTEPICEURL+notePhoto.getName())
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_loading_error)
                            .into((ImageView) helper.getView(R.id.noteimg));
                    Picasso.with(mContext)
                            .load(BASUSERPICEURL+user.getIcon())
                            .placeholder(R.drawable.default_icon)
                            .into((ImageView) helper.getView(R.id.anthorimg));

                    helper.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, NoteDetailActivity.class);
                            intent.putExtra("noteId",note.getId());
                            intent.putExtra("multi",item);
                            startActivity(intent);
                        }
                    });
                    helper.setText(R.id.notetitle,note.getTitle())
                            .setText(R.id.notecontent,note.getContent())
                            .setText(R.id.notepublishtime, CommonUtil.getHowLongAgo(note.getPublish()))
                            .setText(R.id.authorname,user.getNickname());
                }
            });
            Toast.makeText(mContext, "成功获取" + notes.size() + "条数据", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mContext, "暂无新帖子", Toast.LENGTH_SHORT).show();
        }
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
    public void onLoginSuccess(Object resultObject) {
        if (resultObject == null) {
            //注销情况下
            SPUtils.clear();
            mDrawerlayout.closeDrawer(Gravity.START);
            mTextView.setText("登录或注册");
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(LoginActivity.class);
                }
            });
            Picasso.with(this)
                    .load(R.drawable.default_icon)
                    .into(mImageView);
            Toast.makeText(mContext, "注销成功", Toast.LENGTH_SHORT).show();
        } else {
            L.e("登录成功");
            User user = (User) resultObject;
            String icon = user.getIcon();
            //头像设置
            if (!TextUtils.isEmpty(icon)) {
                //异步加载头像
                Picasso.with(this)
                        .load(BASUSERPICEURL + icon)
                        .placeholder(R.drawable.default_icon)
                        //刷新缓存
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(mImageView);
            } else {
                //默认头像
            }
            //文本标签设置
            mTextView.setText(user.getNickname());
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        startActivity(ProfileActivity.class);
                    startActivityForResult(new Intent(mContext, ProfileActivity.class), REQUESTCODE);
                }
            });
        }
    }




    @Override
    public void clearData() {
        mRelistHome.setAdapter(null);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }
}
