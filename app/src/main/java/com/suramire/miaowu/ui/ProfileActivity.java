package com.suramire.miaowu.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;
import com.suramire.miaowu.presenter.ProfilePresenter;
import com.suramire.miaowu.ui.fragment.IntroductionFragment;
import com.suramire.miaowu.ui.fragment.PublishFragment;
import com.suramire.miaowu.ui.fragment.TopicFragment;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action1;

import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/10/24.
 */

public class ProfileActivity extends BaseActivity<ProfilePresenter> implements ProfileContract.View {
    public static final int SUCCESS = 0x101;
    public static final int SELECT_PIC_BY_PICK_PHOTO = 0x102;

    @Bind(R.id.toolbar)
    Toolbar mToolbar4;
    //    @Bind(R.id.list_profile)
//    ListView mListProfile;
    @Bind(R.id.textView8)
    TextView mUserNameLabel;
    @Bind(R.id.imageView10)
    RoundedImageView mUserImageView;
    @Bind(R.id.viewpager_profile)
    ViewPager mViewpagerProfile;
    @Bind(R.id.tl_profile)
    TabLayout mTlProfile;
    private ProgressDialog mProgressDialog;

    private int mId;


    @Override
    public int bindLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void createPresenter() {
        mPresenter = new ProfilePresenter();
    }

    @Override
    public void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        setSupportActionBar(mToolbar4);
        setTitle("个人中心");
//        View emptyView = findViewById(R.id.rl_empty_profile);
//        mListProfile.setEmptyView(emptyView);
        registerForContextMenu(mUserImageView);
        mPresenter.getProfile();
        final ArrayList<Fragment> fragments = new ArrayList<>();
        IntroductionFragment e = new IntroductionFragment();
        PublishFragment e1 = new PublishFragment();
        TopicFragment e2 = new TopicFragment();
        fragments.add(e);
        fragments.add(e2);
        fragments.add(e1);
        final String[] titles = new String[]{"简介", "话题", "帖子"};
        mViewpagerProfile.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mTlProfile.setupWithViewPager(mViewpagerProfile);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem logout = menu.add(0, 10, 0, "logout");
        logout.setIcon(R.drawable.ic_close_black_24dp);
        logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 10: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("是否注销登录")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(SUCCESS);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(true)
                        .show();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.replace: {
                pickPhoto();
            }
            break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            Uri photoUri = data.getData();
            File file = CommonUtil.getFileByUri(photoUri);
            L.e("图片选择成功：" + file.getName());
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", mId + ".png", requestFile);
            String descriptionString = "hello, 这是文件描述";
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString);
            ApiLoader.uploadUserIcon(description, body)
                    .map(new ResponseFunc<Object>())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            ToastUtil.showShortToastCenter("修改头像成功");
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtil.showShortToastCenter("修改头像失败:"+throwable.getMessage());
                        }
                    });

//                            // TODO: 2017/10/25 更新数据用户头像字段
//                            // todo 显示头像
        }
    }

    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.cancel();
    }

    @Override
    public void onSuccess(Object object) {
        User user = (User) object;
        mId = user.getId();
        if (user.getIcon() != null) {
            Picasso.with(mContext)
                    .load(BASUSERPICEURL + user.getIcon())
                    .placeholder(R.drawable.default_icon)
                    .into(mUserImageView);
        }
        String mNickname = user.getNickname();
        mUserNameLabel.setText(mNickname);
    }





    @Override
    public int getUid() {
        return (int) SPUtils.get("uid", 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }


}
