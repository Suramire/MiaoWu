package com.suramire.miaowu.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseFragment;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.presenter.UserPresenter;
import com.suramire.miaowu.ui.FansListActivity;
import com.suramire.miaowu.ui.LoginActivity;
import com.suramire.miaowu.ui.NoteListActivity;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/25.
 */

public class PersonFragment extends BaseFragment<UserPresenter> implements UserContract.View {


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
    @Bind(R.id.tv_note_count)
    TextView tvNoteCount;
    @Bind(R.id.ll_followerlist)
    LinearLayout llFollowerlist;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.ll_mynote)
    LinearLayout llMycourse;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    private ProgressDialog mProgressDialog;
    private Integer uid;

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.dismiss();

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void createPresenter() {
        mPresenter = new UserPresenter();
    }

    @Override
    public void initView() {
        setRequireFresh(true);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
        if(CommonUtil.isLogined()){
            llLogin.setVisibility(View.VISIBLE);
            llNotlogin.setVisibility(View.GONE);
            mPresenter.getUserInfo();
        }else{
            llLogin.setVisibility(View.GONE);
            llNotlogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_person;
    }


    @OnClick({R.id.btn_login, R.id.img_icon, R.id.ll_followlist, R.id.ll_followerlist, R.id.ll_mynote,R.id.ll_note2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                startActivityForResult(LoginActivity.class,ApiConfig.LOGINREQUESTCODE);
                getActivity().startActivityForResult(new Intent(getActivity(), LoginActivity.class),ApiConfig.LOGINREQUESTCODE);
                break;
            case R.id.img_icon:
                break;
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


    @Override
    public int getUid() {
        return CommonUtil.getCurrentUid();
    }

    @Override
    public void onGetInfoSuccess(User userinfo) {
        L.e("成功获取用户信息:" + userinfo);
        PicassoUtil.show(ApiConfig.BASUSERPICEURL + userinfo.getIcon(), imgIcon);
        tvUsername.setText(userinfo.getNickname());
        uid = userinfo.getId();
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

    @Override
    public void onGetRelationshipSuccess(int type) {

    }


}
