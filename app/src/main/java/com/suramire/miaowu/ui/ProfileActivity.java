package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.presenter.UserPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Suramire on 2018/1/27.
 */

public class ProfileActivity extends BaseActivity<UserPresenter> implements UserContract.View {


    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.ll_notlogin)
    LinearLayout llNotlogin;
    @Bind(R.id.img_icon)
    RoundedImageView imgIcon;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_signal)
    TextView tvSignal;
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
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.tv_title_note)
    TextView tvTitleNote;
    @Bind(R.id.ll_mynote)
    LinearLayout llMynote;
    @Bind(R.id.tv_title_reply)
    TextView tvTitleReply;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    private ProgressDialog progressDialog;
    private int uid;

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
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_person;
    }

    @Override
    public void createPresenter() {
        mPresenter = new UserPresenter();
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍候……");
        //获取其他用户编号
        uid = getIntent().getIntExtra("uid", 0);
        if (uid != 0) {
            mPresenter.getUserInfo();
            llLogin.setVisibility(View.VISIBLE);
            llNotlogin.setVisibility(View.GONE);
        } else {
            llLogin.setVisibility(View.GONE);
            llNotlogin.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public void onGetInfoSuccess(User userinfo) {
        L.e("成功获取其他用户信息:" + userinfo);
        PicassoUtil.show(ApiConfig.BASUSERPICEURL + userinfo.getIcon(), imgIcon);
        tvUsername.setText(userinfo.getNickname());
        tvTitleNote.setText(userinfo.getNickname() + "的帖子");
        tvTitleReply.setText(userinfo.getNickname() + "的回复");

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
    }



    @OnClick({R.id.ll_followlist, R.id.ll_followerlist, R.id.ll_note2, R.id.ll_mynote})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_followlist:
                // TODO: 2018/1/30 跳转到关注列表
                Intent intent1 = new Intent(mContext, FansListActivity.class);
                intent1.putExtra("index",0);
                intent1.putExtra("uid",uid);
                startActivity(intent1);
                break;
            case R.id.ll_followerlist:
                // TODO: 2018/1/30 跳转到粉丝列表
                Intent intent2 = new Intent(mContext, FansListActivity.class);
                intent2.putExtra("index",1);
                intent2.putExtra("uid",uid);
                startActivity(intent2);
                break;
            case R.id.ll_note2:
            case R.id.ll_mynote:
                Intent intent = new Intent(mContext, NoteListActivity.class);
                intent.putExtra("uid", getUid());
                startActivity(intent);
                break;
        }
    }
}
