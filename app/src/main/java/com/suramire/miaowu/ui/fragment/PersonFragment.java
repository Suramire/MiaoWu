package com.suramire.miaowu.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseFragment;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.presenter.LoginPresenter;
import com.suramire.miaowu.ui.LoginActivity;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/25.
 */

public class PersonFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {


    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.ll_notlogin)
    LinearLayout llNotlogin;
    @Bind(R.id.img_settings)
    ImageView imgSettings;
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
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.ll_mycourse)
    LinearLayout llMycourse;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    private ProgressDialog mProgressDialog;

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
        mPresenter = new LoginPresenter();
    }

    @Override
    public void initView() {
        setRequireFresh(true);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
        if(CommonUtil.isLogined()){
            llLogin.setVisibility(View.VISIBLE);
            llNotlogin.setVisibility(View.GONE);
            mPresenter.getUserInfo(CommonUtil.getCurrentUid());
        }else{
            llLogin.setVisibility(View.GONE);
            llNotlogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_person;
    }


    @OnClick({R.id.btn_login, R.id.img_settings, R.id.img_icon, R.id.ll_followlist, R.id.ll_followerlist, R.id.ll_mycourse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                startActivityForResult(LoginActivity.class,ApiConfig.LOGINREQUESTCODE);
                getActivity().startActivityForResult(new Intent(getActivity(), LoginActivity.class),ApiConfig.LOGINREQUESTCODE);
                break;
            case R.id.img_settings:
                SPUtils.put("uid",0);
                break;
            case R.id.img_icon:
                break;
            case R.id.ll_followlist:
                break;
            case R.id.ll_followerlist:
                break;
            case R.id.ll_mycourse:
                break;
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
    public void onGetInfoSuccess(User userinfo) {
        L.e("成功获取用户信息:" + userinfo);
        PicassoUtil.load(ApiConfig.BASUSERPICEURL + userinfo.getIcon())
                .into(imgIcon);
        tvUsername.setText(userinfo.getNickname());
    }


}
