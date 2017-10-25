package com.suramire.miaowu.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.presenter.ProfilePresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.view.IProfileView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/24.
 */

public class ProfileActivity extends BaseActivity implements IProfileView {
    public static final int SUCCESS = 0x101;
    public static final int SELECT_PIC_BY_PICK_PHOTO = 0x102;

    @Bind(R.id.toolbar4)
    Toolbar mToolbar4;
    @Bind(R.id.list_profile)
    ListView mListProfile;
    @Bind(R.id.textView8)
    TextView mUserNameLabel;
    @Bind(R.id.imageView10)
    RoundedImageView mUserImageView;
    private ProgressDialog mProgressDialog;

//    private ImageView mUserImageView;
//    private TextView mUserNameLabel;
    private ProfilePresenter mProfilePresenter;
    private int mId;

    @Override
    protected String getTitleString() {
        return "个人中心";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView(View view) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        setSupportActionBar(mToolbar4);
        View emptyView = findViewById(R.id.rl_empty_profile);
        mListProfile.setEmptyView(emptyView);
        registerForContextMenu(mUserImageView);
        mProfilePresenter = new ProfilePresenter(this);
        mProfilePresenter.getProfile();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
            HTTPUtil.getPost(BASEURL + "getPicUser", file, mId + ".png", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    L.e("图片上传失败：" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    M m = (M) GsonUtil.jsonToObject(result, M.class);
                    switch (m.getCode()) {
                        case M.CODE_SUCCESS: {
                            L.e("图片上传成功");
                            // TODO: 2017/10/25 更新数据用户头像字段
                            // todo 显示头像
                        }
                        break;
                        case M.CODE_FAILURE: {
                            L.e("图片上传失败：" + m.getMessage());
                        }
                        break;
                        case M.CODE_ERROR: {
                            L.e("图片上传出错：" + m.getMessage());
                        }
                        break;
                    }
                }
            });
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
    public void onGetProfileSuccess(Object object) {

        User user = (User) object;
        mId = user.getId();
        if (user.getIcon() != null) {


            Picasso.with(mContext)
                    .load(BASEURL + "upload/" + user.getIcon())
                    .placeholder(R.drawable.default_icon)
                    .into(mUserImageView);
        }
        String mNickname = user.getNickname();
        mUserNameLabel.setText(mNickname);

        ArrayList<String> list = new ArrayList<>();
        list.add(BASEURL + "upload/cat.jpg");
        list.add(BASEURL + "upload/cat1.jpg");
        list.add(BASEURL + "upload/cat2.jpg");
        list.add(BASEURL + "upload/cat3.jpg");
        list.add(BASEURL + "upload/cat4.jpg");
        final String icon = BASEURL + "upload/0000.png";

        mListProfile.setAdapter(new CommonAdapter<String>(mContext, R.layout.item_list, list) {

            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {

                Picasso.with(mContext)
                        .load(item)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_loading_error)
                        .into((ImageView) helper.getView(R.id.imageView3));
                Picasso.with(mContext)
                        .load(icon)
                        .placeholder(R.drawable.default_icon)
                        .into((ImageView) helper.getView(R.id.imageView2));

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
    public void onGetProfileFaiure(String failureMessage) {
        Toast.makeText(mContext, "错误用户信息失败：", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onGetProfileError(String errorMessage) {
        Toast.makeText(mContext, "获取用户信息出现错误：", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public int getUid() {
        return (int) SPUtils.get("uid", 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        MenuItem replace = menu.add(0, 0, 0x666, "更换相册封面");
//        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }


}
