package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.presenter.LoginPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @Bind(R.id.toolbar)
    Toolbar mToolbar3;
    @Bind(R.id.edt_name)
    EditText mEdtName;
    @Bind(R.id.tl_username)
    TextInputLayout mTlUsername;
    @Bind(R.id.edt_password)
    EditText mEdtPassword;
    @Bind(R.id.tl_password)
    TextInputLayout mTlPassword;
    @Bind(R.id.ll_login)
    LinearLayout mLlLogin;
    private ProgressDialog mProgressDialog;

    public String getNameString() {
        return mNameString;
    }

    public void setNameString(String nameString) {
        mNameString = nameString;
    }

    private String mNameString;
    private String mPasswordString;

    public String getPasswordString() {
        return mPasswordString;
    }

    public void setPasswordString(String passwordString) {
        mPasswordString = passwordString;
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void createPresenter() {
        mPresenter =new LoginPresenter();
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar3);
        getSupportActionBar().setTitle("登录" + getResources().getString(R.string.app_name));
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
    }

    @OnClick({R.id.edt_name, R.id.edt_password, R.id.btn_login, R.id.tv_reg, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_name:
                break;
            case R.id.edt_password:
                break;
            case R.id.btn_login:
                mPresenter.login(null,null);
                break;
            case R.id.tv_reg:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget:
                break;
        }
    }

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
        ToastUtil.showShortToastCenter("登录成功");
        User user = (User) data;
        //保存用户登录信息
        SPUtils.put("uid", user.getId());
        SPUtils.put("nickname", user.getNickname());
        SPUtils.put("password", user.getPassword());
        SPUtils.put("autologin",1);
        setResult(ApiConfig.RESULTCODE);
        finish();

    }

    @Override
    public String getUserName() {
        setNameString(mEdtName.getText().toString().trim());
        return getNameString();
    }

    @Override
    public String getPassword() {
        setPasswordString(mEdtPassword.getText().toString().trim());
        return getPasswordString();
    }

    @Override
    public void onGetInfoSuccess(User userinfo) {

    }

}
