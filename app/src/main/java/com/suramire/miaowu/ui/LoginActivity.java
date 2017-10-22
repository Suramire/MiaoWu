package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.presenter.LoginPresenter;
import com.suramire.miaowu.view.ILoginView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    @Bind(R.id.toolbar3)
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
                mLoginPresenter.login();
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
    public String getUserName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }
}
