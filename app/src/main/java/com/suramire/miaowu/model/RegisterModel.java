package com.suramire.miaowu.model;

import android.text.TextUtils;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.Constant;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Suramire on 2017/10/22.
 */

public class RegisterModel implements IRegisterModel {


    public RegisterModel() {


    }

    @Override
    public void validatePhoneNumber(final String phoneNumber, final OnGetResultListener onValidationListener) {
        //判断手机号是否被注册
        if(CommonUtil.isMobileNumber(phoneNumber)) {
            User user = new User(phoneNumber, null, null);
            HTTPUtil.getPost(Constant.BASEURL + "checkPhoneUser", user, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onValidationListener.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    try {
                        M m = (M) GsonUtil.jsonToObject(result, M.class);
                        switch (m.getCode()) {
                            case M.CODE_SUCCESS: {
                                onValidationListener.onSuccess(null);
                            }
                            break;
                            case M.CODE_FAILURE: {
                                onValidationListener.onFailure(m.getMessage());
                            }
                            break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        onValidationListener.onError(e.getMessage());
                    }
                }
            });
        }else{
            onValidationListener.onFailure("请输入正确的手机号码");
        }
    }

    @Override
    public void validateRegisterInformation(String phoneNumber,String userName, String password, final String rePassword, final OnGetResultListener onValidationListener) {
        // 信息校验
        //验证成功提交注册信息 并注册
        if(TextUtils.isEmpty(phoneNumber)|| TextUtils.isEmpty(password)|| TextUtils.isEmpty(rePassword)){
            onValidationListener.onFailure("请将注册信息填写完整");
        }else if(CommonUtil.isMobileNumber(userName)){
            onValidationListener.onFailure("不能使用手机号格式的用户名，请重新输入");
        }else if(rePassword.equals(password)){
            //转成json传送
            User user = new User(phoneNumber, userName, password);
            HTTPUtil.getPost(Constant.BASEURL + "addUser", user, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onValidationListener.onFailure(e.getMessage());
                }
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    M m = (M) GsonUtil.jsonToObject(result, M.class);
                    switch (m.getCode()){
                        case M.CODE_SUCCESS:{
                            onValidationListener.onSuccess(null);
                        }break;
                        case M.CODE_FAILURE:{
                            onValidationListener.onFailure(m.getMessage());
                        }break;
                        case M.CODE_ERROR:{
                            onValidationListener.onError(m.getMessage());
                        }
                    }
                }
            });
        }else if(!rePassword.equals(password)){
            onValidationListener.onFailure("两次输入的密码不一致，请重新输入");
        }else {
            onValidationListener.onError("注册出现异常，请检查注册信息");
        }
    }


}
