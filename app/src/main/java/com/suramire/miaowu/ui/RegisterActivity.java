package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.presenter.RegisterPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.view.IRegisterView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Suramire on 2017/10/17.
 */

public class RegisterActivity extends BaseActivity implements IRegisterView{
    @Bind(R.id.toolbar_register)
    Toolbar mToolbarRegister;
    @Bind(R.id.editTextPhoneNumber)
    TextInputEditText mEditTextPhone;
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
    private ProgressDialog mProgressDialog;
    private RegisterPresenter mRegisterPresenter;
    private EventHandler mEventHandler;


    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbarRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViews = new ViewGroup[]{mLlPhone, mLlValidation, mLlNamed};
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        mRegisterPresenter = new RegisterPresenter(this);
        mEventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onRegisterError(msg);
                        }
                    });
                    // {"status":468,"detail":"需要校验的验证码错误"}
                    // 短信上限 {"status":477,"detail":"当前手机号发送短信的数量超过限额"}

                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 成功发送短信
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                goStep1();
                            }
                        });
                        L.e("成功发送验证码");

                    }
                    else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //验证成功 进入密码设置界面
                                goStep2();
                            }
                        });
                        L.e("成功验证验证码");
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);

    }


    @OnClick({R.id.editTextPhoneNumber, R.id.btn_register_next, R.id.editText_validation, R.id.btn_register_next_validation,
            R.id.editText_name, R.id.editText_pwd, R.id.editText_repwd, R.id.btn_register_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
// step0
            case R.id.btn_register_next:{
                mRegisterPresenter.validatePhoneNumber(new OnGetResultListener() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SMSSDK.getVerificationCode("86", getPhoneNumber());
                            }
                        });
                    }

                    @Override
                    public void onFailure(final String failtureMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onRegisterFailure(failtureMessage);
                            }
                        });
                    }

                    @Override
                    public void onError(final String errorMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onRegisterError(errorMessage);
                            }
                        });
                    }
                });

            }break;
//step1

            case R.id.btn_register_next_validation: {
                String validationNumber = mEditTextValidation.getText().toString().trim();
                SMSSDK.submitVerificationCode("86",getPhoneNumber(),validationNumber);
            }break;


//step2
            case R.id.btn_register_confirm:{
                mRegisterPresenter.validateInformation();
            }break;

            case R.id.editText_name:{
                mTlValidationName.setErrorEnabled(false);
            }
                break;
            case R.id.editText_pwd:{
                mTlValidationPwd.setErrorEnabled(false);
            }
                break;
            case R.id.editText_repwd:{
                mTlValidationRepwd.setErrorEnabled(false);
            }
                break;
            case R.id.editTextPhoneNumber: {
                mTlPhone.setErrorEnabled(false);
            }
            break;
            case R.id.editText_validation: {
                mTlValidation.setErrorEnabled(false);
            }break;
        }
    }

    @Override
    protected String getTitleString() {
        return "注册";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //若已进入验证码填写界面则返回到手机号填写界面
            if (step == 1) {
                goStep0();
            }
            //若已经进入密码设置界面则返回至验证码界面
            else if(step ==2){
                goStep1();
            }
            //若已进入手机号填写界面则返回登录界面
            else {
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
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
    public void onRegisterSuccess() {
        Toast.makeText(RegisterActivity.this, "注册成功，请前往登录！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRegisterFailure(String failureMessage) {
        Toast.makeText(RegisterActivity.this, "注册失败："+failureMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterError(String errorMessage) {
        Toast.makeText(RegisterActivity.this, "注册出现错误:"+errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public String getPhoneNumber() {
        return mEditTextPhone.getText().toString().trim();
    }

    @Override
    public String getValidationNumber() {
        return mEditTextValidation.getText().toString().trim();
    }

    @Override
    public String getUserName() {
        return mEditTextName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEditTextPwd.getText().toString().trim();
    }

    @Override
    public String getRePassword() {
        return mEditTextRepwd.getText().toString().trim();
    }

    @Override
    public void goStep0() {
        CommonUtil.switchVisiable(mLlPhone,mViews);
        mEditTextValidation.setText(null);
        step = 0;
    }

    @Override
    public void goStep1() {
        CommonUtil.switchVisiable(mLlValidation,mViews);
        mEditTextValidation.requestFocus();
        step = 1;
    }

    @Override
    public void goStep2() {
        CommonUtil.switchVisiable(mLlNamed,mViews);
        mEditTextName.requestFocus();
        step = 2;
    }
}
