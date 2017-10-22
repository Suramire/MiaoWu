package com.suramire.miaowu.ui;

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
import com.suramire.miaowu.base.App;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.Constant;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private EventHandler mEventHandler;
    private String mPhoneNumber;


    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbarRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViews = new ViewGroup[]{mLlPhone, mLlValidation, mLlNamed};
        mEventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    L.e("error:" + msg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(App.getApp(), "错误："+msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                    // {"status":468,"detail":"需要校验的验证码错误"}
                    // 短信上限 {"status":477,"detail":"当前手机号发送短信的数量超过限额"}

                    //验证码错误
                } else {
                    L.e("event:" + event);
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 成功发送短信
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtil.switchVisiable(mLlValidation,mViews);
                                mEditTextValidation.requestFocus();
                                step = 1;
                            }
                        });
                        L.e("成功发送验证码");

                    }
                    else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //验证成功 进入密码设置界面
                                CommonUtil.switchVisiable(mLlNamed,mViews);
                                mEditTextName.requestFocus();
                                step = 2;
                            }
                        });


                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(mEventHandler);
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
                mPhoneNumber = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    CommonUtil.showError(mTlPhone, "手机号不能为空！");
                } else {
                    // TODO: 2017/10/20 手机号码格式判断
                    // TODO: 2017/10/22 已经注册的手机号不能再用于注册
                    //这里从数据库读取该手机号是否已经被注册
                    SMSSDK.getVerificationCode("86", mPhoneNumber);

                }
                break;
            case R.id.editText_validation: {
                mTlValidation.setErrorEnabled(false);
            }
            break;
            case R.id.btn_register_next_validation: {
                //验证码验证
                String validationNumber = mEditTextValidation.getText().toString().trim();
                SMSSDK.submitVerificationCode("86",mPhoneNumber,validationNumber);
            }
            break;
            case R.id.editText_name:
                break;
            case R.id.editText_pwd:
                break;
            case R.id.editText_repwd:
                break;
            case R.id.btn_register_confirm:
                // TODO: 2017/10/21 注册信息验证
                //生成user对象
                String name = mEditTextName.getText().toString().trim();
                String password = mEditTextPwd.getText().toString().trim();
                String rePassword = mEditTextRepwd.getText().toString().trim();

                String json = GsonUtil.objectToJson(new User(mPhoneNumber, name, password));
                //转成json传送
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("json", json);
                HTTPUtil.getPost(Constant.BASEURL + "addUser", map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "注册失败,无法连接至服务器！", Toast.LENGTH_SHORT).show();
                            }
                        });
                        L.e("onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        if("error".equals(result)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册失败,请检查用户名是否重复！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册成功，请前往登录！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                });


                //服务器接收json 解析出对象并保存

                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}
