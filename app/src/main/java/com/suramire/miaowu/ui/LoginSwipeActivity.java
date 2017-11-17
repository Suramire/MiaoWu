package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.contract.LoginContract;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.presenter.LoginPresenter;
import com.suramire.miaowu.util.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginSwipeActivity extends BaseSwipeActivity implements LoginContract.View {
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
    private LoginPresenter mLoginPresenter;

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
    protected String getTitleString() {
        return "登录" + getResources().getString(R.string.app_name);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar3);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        mLoginPresenter = new LoginPresenter(this);
    }

    @OnClick({R.id.edt_name, R.id.edt_password, R.id.btn_login, R.id.tv_reg, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_name:
                break;
            case R.id.edt_password:
                break;
            case R.id.btn_login:
                mLoginPresenter.login(null,null);
                break;
            case R.id.tv_reg:
                startActivity(RegisterSwipeActivity.class);
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
    public void onLoginSuccess(Object resultObject) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        User user = (User) resultObject;
        //保存用户登录信息
        SPUtils.put("uid", user.getId());
        SPUtils.put("nickname", user.getNickname());
        SPUtils.put("password", user.getPassword());
        SPUtils.put("autologin",1);
        finish();
    }

    @Override
    public void onLoginFailure(String failureMessage) {
        Toast.makeText(this, "登录失败:"+failureMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginError(String errorMessage) {
        Toast.makeText(this, "登录出现错误:"+errorMessage, Toast.LENGTH_SHORT).show();
    }
}
