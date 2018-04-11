package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.presenter.UserPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Suramire on 2018/1/27.
 * 他人信息查看
 */

public class ProfileActivity extends BaseActivity<UserPresenter> implements UserContract.View {


    @Bind(R.id.img_icon)
    RoundedImageView imgIcon;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_follow_count)
    TextView tvFollowCount;
    @Bind(R.id.ll_followlist)
    LinearLayout llFollowlist;
    @Bind(R.id.tv_follower_count)
    TextView tvFollowerCount;
    @Bind(R.id.ll_followerlist)
    LinearLayout llFollowerlist;
    @Bind(R.id.tv_note_count)
    TextView tvNoteCount;
    @Bind(R.id.ll_note2)
    LinearLayout llNote2;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.imageButton)
    ImageButton imageButton;
    @Bind(R.id.tv_title_note)
    TextView tvTitleNote;
    @Bind(R.id.ll_mynote)
    LinearLayout llMynote;
    @Bind(R.id.tv_title_reply)
    TextView tvTitleReply;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    private ProgressDialog progressDialog;
    private int uid;
    private int type;
    private Integer userId;

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void cancelLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {
        String s = (String) data;
        if ("follow".equals(s)) {
            ToastUtil.showShortToastCenter("成功关注该用户");
            // TODO: 2018/1/30 按钮文字样式变化
            // TODO: 2018/1/30 操作前 登录判断

        } else {
            ToastUtil.showShortToastCenter("成功取消关注该用户");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_person;
    }

    @Override
    public void createPresenter() {
        mPresenter = new UserPresenter();
    }

    @Override
    public void initView() {
        toolbar.setTitle("个人中心");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        toolbarTextRight.setText("详细设置");
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍候……");
        //获取其他用户编号
        uid = getIntent().getIntExtra("uid", 0);
        mPresenter.getUserInfo();
    }


    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public void onGetInfoSuccess(User userinfo) {
        L.e("成功获取其他用户信息:" + userinfo);
        userId = userinfo.getId();
        PicassoUtil.show(ApiConfig.BASUSERPICEURL + userinfo.getIcon(), imgIcon);
        tvUsername.setText(userinfo.getNickname());
        if (userId == CommonUtil.getCurrentUid()) {
            tvTitleNote.setText("我的帖子");
            tvTitleReply.setText("我的回复");
            toolbarTextRight.setVisibility(View.VISIBLE);
        } else {
            tvTitleNote.setText(userinfo.getNickname() + "的帖子");
            tvTitleReply.setText(userinfo.getNickname() + "的回复");
            toolbarTextRight.setVisibility(View.GONE);
        }


    }

    @Override
    public void onGetUserFollowCountSuccess(int count) {
        tvFollowCount.setText(String.valueOf(count));

    }

    @Override
    public void onGetUserFollowerCountSuccess(int count) {
        tvFollowerCount.setText(String.valueOf(count));
    }

    @Override
    public void onGetUserNoteCountSuccess(int count) {
        tvNoteCount.setText(String.valueOf(count));
        //非自身对象查询好友关系
        if (userId != CommonUtil.getCurrentUid()) {
            mPresenter.getRelationship();
        }

    }

    @Override
    public void onGetRelationshipSuccess(int type) {
        imageButton.setVisibility(View.VISIBLE);
        this.type = type;
        switch (type) {
            case 0:
                imageButton.setVisibility(View.GONE);
                break;//未登录用户
            case 1:
                imageButton.setImageResource(R.mipmap.btn_unfollow);
                break;//登录用户关注页面用户
            case 2:
                imageButton.setImageResource(R.mipmap.btn_follow);
                break;//用户未关注页面用户
            case 3:
                imageButton.setImageResource(R.mipmap.btn_follow_eachother);
                ;
                break;//互相关注
        }
    }


    @OnClick({R.id.ll_followlist, R.id.ll_followerlist, R.id.ll_note2, R.id.ll_mynote,
            R.id.imageButton, R.id.toolbar_image_left, R.id.toolbar_text_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_followlist:
                // TODO: 2018/1/30 跳转到关注列表
                Intent intent1 = new Intent(mContext, FansListActivity.class);
                intent1.putExtra("index", 0);
                intent1.putExtra("uid", uid);
                startActivity(intent1);
                break;
            case R.id.ll_followerlist:
                // TODO: 2018/1/30 跳转到粉丝列表
                Intent intent2 = new Intent(mContext, FansListActivity.class);
                intent2.putExtra("index", 1);
                intent2.putExtra("uid", uid);
                startActivity(intent2);
                break;
            case R.id.ll_note2:
            case R.id.ll_mynote:
                Intent intent = new Intent(mContext, NoteListActivity.class);
                intent.putExtra("uid", getUid());
                startActivity(intent);
                break;
            case R.id.imageButton: {
                // TODO: 2018/1/30 这里根据情况进行关注/取消关注操作
                switch (type) {
                    case 2:
                        mPresenter.follow();
                        break;//关注
                    case 1:
                    case 3:
                        mPresenter.unfollow();
                        break;//取消关注
                }
            }
            break;
            case R.id.toolbar_image_left:
                setResult(ApiConfig.RESULTCODE_NOTIFICATION);
                finish();
                break;
            case R.id.toolbar_text_right:
                Intent intent3 = new Intent(mContext, ProfileDetailActivity.class);
                intent3.putExtra("uid", CommonUtil.getCurrentUid());
                startActivityForResult(intent3,ApiConfig.LOGINREQUESTCODE);
                break;
        }
    }



}
