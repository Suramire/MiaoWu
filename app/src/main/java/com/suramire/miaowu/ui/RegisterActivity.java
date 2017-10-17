package com.suramire.miaowu.ui;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.util.CommonUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/17.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.toolbar_register)
    Toolbar mToolbarRegister;
    @Bind(R.id.editText)
    TextInputEditText mEditText;
    @Bind(R.id.tl_phone)
    TextInputLayout mTlPhone;
    @Bind(R.id.btn_register_next)
    Button mBtnRegisterNext;
    @Bind(R.id.ll_phone)
    LinearLayout mLlPhone;
    @Bind(R.id.editText_validation)
    TextInputEditText mEditTextValidation;
    @Bind(R.id.tl_validation)
    TextInputLayout mTlValidation;
    @Bind(R.id.btn_register_next_validation)
    Button mBtnRegisterNextValidation;
    @Bind(R.id.ll_validation)
    LinearLayout mLlValidation;
    @Bind(R.id.editText_name)
    TextInputEditText mEditTextName;
    @Bind(R.id.tl_validation_name)
    TextInputLayout mTlValidationName;
    @Bind(R.id.editText_pwd)
    TextInputEditText mEditTextPwd;
    @Bind(R.id.tl_validation_pwd)
    TextInputLayout mTlValidationPwd;
    @Bind(R.id.editText_repwd)
    TextInputEditText mEditTextRepwd;
    @Bind(R.id.tl_validation_repwd)
    TextInputLayout mTlValidationRepwd;
    @Bind(R.id.btn_register_confirm)
    Button mBtnRegisterConfirm;
    @Bind(R.id.ll_named)
    LinearLayout mLlNamed;

    //注册进度标志位
    // 1=进入验证码填写界面
    // 0=进入手机号填写界面
    // 2=进入密码设置界面
    private int step;
    private ViewGroup[] mViews;



    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbarRegister);
        getSupportActionBar().setTitle("注册");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViews = new ViewGroup[]{mLlPhone, mLlValidation, mLlNamed};
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @OnClick({R.id.editText, R.id.btn_register_next, R.id.editText_validation, R.id.btn_register_next_validation,
            R.id.editText_name, R.id.editText_pwd, R.id.editText_repwd, R.id.btn_register_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editText: {
                mTlPhone.setErrorEnabled(false);
            }
            break;
            case R.id.btn_register_next:
                //手机号验证
                String phoneNumber = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    CommonUtil.showError(mTlPhone, "手机号不能为空！");
                } else {
                    CommonUtil.switchVisiable(mLlValidation,mViews);
                    mEditTextValidation.requestFocus();
                    step = 1;
                }
                break;
            case R.id.editText_validation: {
                mTlValidation.setErrorEnabled(false);
            }
            break;
            case R.id.btn_register_next_validation: {
                //验证码验证
                String validationNumber = mEditTextValidation.getText().toString().trim();
                if ("1234".equals(validationNumber)) {
                    //验证成功 进入密码设置界面
                    CommonUtil.switchVisiable(mLlNamed,mViews);
                    mEditTextName.requestFocus();
                    step = 2;
                } else {
                    Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.editText_name:
                break;
            case R.id.editText_pwd:
                break;
            case R.id.editText_repwd:
                break;
            case R.id.btn_register_confirm:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //若已进入验证码填写界面则返回到手机号填写界面
            if (step == 1) {
                CommonUtil.switchVisiable(mLlPhone,mViews);
                mEditTextValidation.setText(null);
                step = 0;
            }
            //若已经进入密码设置界面则返回至验证码界面
            else if(step ==2){
                CommonUtil.switchVisiable(mLlValidation,mViews);
                mEditTextValidation.setText(null);
                step =1;
            }
            //若已进入手机号填写界面则返回登录界面
            else {
                finish();
            }
        }
        return true;
    }

}
