package com.suramire.miaowu.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.PasswordContract;
import com.suramire.miaowu.presenter.PasswordPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class PasswordActivity extends BaseActivity<PasswordPresenter> implements PasswordContract.View{
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_verifycode)
    EditText edtVerifycode;
    @Bind(R.id.edt_passwrod1)
    EditText edtPasswrod1;
    @Bind(R.id.edt_passwrod2)
    EditText edtPasswrod2;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.ll_password)
    LinearLayout llPassword;
    private boolean done;
    private boolean clicked;
    private boolean sendCode;
    private boolean verifyCode;
//    private int uid;
    private String password;
    private boolean isPhoneOk;
    private String mPhone;

    @Override
    public int bindLayout() {
        return R.layout.activity_password;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PasswordPresenter();
    }

    @Override
    public void initView() {
        setTitle("修改密码");
        progressDialog.setMessage("请稍候……");
//        uid = getIntent().getIntExtra("uid", 0);
        //短信验证码相关
        EventHandler mEventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showLongToastCenter(msg);
//                            {"status":477,"detail":"the sending messages of this phone exceeds the limit"}
                        }
                    });
                } else {
                    L.e("event:" + event);
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 成功发送短信
                        sendCode = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showLongToastCenter("已发送验证码，请查收短信");
                            }
                        });
                        L.e("成功发送验证码");
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        verifyCode = true;
//                        ToastUtil.showLongToastCenter("成功验证验证码");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llCode.setVisibility(View.GONE);
                                llPassword.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);

    }

    @Override
    public void onSuccess(Object data) {
        done = true;
        ToastUtil.showLongToastCenter("已成功修改密码，请重新登录系统");
    }

    @OnClick({R.id.btn_getverifycode,R.id.btn_verifycode, R.id.btn_modifypassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getverifycode:

                mPhone = edtPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(mPhone) && CommonUtil.isMobileNumber(mPhone)){
                    if(isPhoneOk){
                        if(!clicked){
                            SMSSDK.getVerificationCode("86", mPhone);
                            clicked = true;
                        }else{
                            ToastUtil.showLongToastCenter("已发送了验证码，请勿频繁点击");
                        }
                    }else{
                        //先判断手机号是否已注册
                        mPresenter.checkPhone();
                    }

                }else{
                    ToastUtil.showLongToastCenter("请输入正确的手机号码格式");
                }
                break;

            case R.id.btn_verifycode:{
                String code = edtVerifycode.getText().toString().trim();
                L.e("code.length()" + code.length());
                if(code.length()!=4){
                    ToastUtil.showLongToastCenter("请输入4位验证码");
                }else{
                    //服务器收到验证码时
                    if(sendCode){
                        SMSSDK.submitVerificationCode("86",mPhone,code);
                    }else{
                        ToastUtil.showLongToastCenter("服务器尚未收到验证码");
                    }
                }
            }break;
            case R.id.btn_modifypassword:
                //未进行修改操作
                if (!done) {
                    //已点击发送验证码按钮
                    if(clicked){
                        //已验证手机号
                        if(verifyCode){
                            String password1 = edtPasswrod1.getText().toString().trim();
                            String password2 = edtPasswrod1.getText().toString().trim();
                            if(TextUtils.isEmpty(password1)||TextUtils.isEmpty(password2)){
                                ToastUtil.showLongToastCenter("新密码不能为空");
                            }else if(password1.equals(password2)){
                                password = password2;
                                mPresenter.modify();
                            }else{
                                ToastUtil.showLongToastCenter("两次输入的密码不一致");
                            }
                        //未验证手机号
                        }else{
                            ToastUtil.showLongToastCenter("验证码不正确,请重试");
                        }
                    }else{
                        ToastUtil.showLongToastCenter("请先进行手机号码验证");
                    }
                }else{
                    ToastUtil.showLongToastCenter("已成功修改密码，请重新登录系统");
                }
                break;
        }
    }

    @Override
    public User getUser() {
        User user = new User();
//        user.setId(uid);
        user.setPhonenumber(mPhone);
        user.setPassword(password);
        return user;
    }

    @Override
    public String getPhoneNumber() {
        return mPhone;
    }

    @Override
    public void onCheckPhoneSuccess() {
        isPhoneOk = true;
    }

    @Override
    public void onCheckPhoneFailed() {
        isPhoneOk = false;
        ToastUtil.showLongToastCenter("该手机号尚未注册，请重新输入");
    }
}
