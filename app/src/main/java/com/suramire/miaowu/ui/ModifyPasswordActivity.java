package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.PasswordContract;
import com.suramire.miaowu.presenter.PasswordPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ModifyPasswordActivity extends BaseActivity<PasswordPresenter> implements PasswordContract.View{
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_verifycode)
    EditText edtVerifycode;
    @Bind(R.id.edt_passwrod1)
    EditText edtPasswrod1;
    @Bind(R.id.edt_passwrod2)
    EditText edtPasswrod2;
    private ProgressDialog progressDialog;
    private boolean done;
    private boolean clicked;
    private boolean sendCode;
    private boolean verifyCode;
    private String phone;
    private int uid;
    private String password;

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
        toolbar.setTitle("修改密码");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("请稍候……");
        uid = getIntent().getIntExtra("uid", 0);
        //短信验证码相关
        EventHandler mEventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShortToastCenter(msg);
//                            {"status":477,"detail":"the sending messages of this phone exceeds the limit"}
                        }
                    });
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 成功发送短信
                        sendCode = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showLongToast("已发送验证码，请查收短信");
                            }
                        });
                        L.e("成功发送验证码");
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        verifyCode = true;
                        L.e("成功验证验证码");
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);
        edtVerifycode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String code = s.toString().trim();
                if(code.length()!=4){
//                    ToastUtil.showLongToast("请输入4位验证码");
                }else{
                    //服务器收到验证码时
                    if(sendCode){
                        SMSSDK.submitVerificationCode("86",phone,code);
                    }else{
                        ToastUtil.showLongToast("服务器尚未收到验证码");
                    }
                }
            }
        });

    }

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
        ToastUtil.showLongToast("修改成功，重新登录以生效");
        done = true;
    }

    @OnClick({R.id.toolbar_image_left, R.id.btn_getverifycode, R.id.btn_modifypassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                finish();
                break;
            case R.id.btn_getverifycode:
                phone = edtPhone.getText().toString().trim();
                if(CommonUtil.isMobileNumber(phone)){
                    if(!clicked){
                        SMSSDK.getVerificationCode("86", phone);
                        clicked = true;
                    }else{
                        ToastUtil.showLongToast("已发送了验证码，请勿频繁点击");
                    }
                }else{
                    ToastUtil.showLongToast("请输入正确的手机号码格式");
                }
                break;
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
                                ToastUtil.showLongToast("新密码不能为空");
                            }else if(password1.equals(password2)){
                                password = password2;
                                mPresenter.modify();
                            }else{
                                ToastUtil.showLongToast("两次输入的密码不一致");
                            }
                        //未验证手机号
                        }else{
                            ToastUtil.showShortToastCenter("验证码不正确");
                        }
                    }else{
                        ToastUtil.showLongToast("请先进行手机号码验证");
                    }
                }else{
                    ToastUtil.showLongToast("已进行修改密码操作，请重新登录");
                }
                break;
        }
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setId(uid);
        user.setPhonenumber(phone);
        user.setPassword(password);
        return user;
    }
}
