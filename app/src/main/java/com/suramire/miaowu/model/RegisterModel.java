package com.suramire.miaowu.model;

import android.text.TextUtils;

import com.suramire.miaowu.listener.OnValidationListener;
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
    public void validatePhoneNumber(final String phoneNumber, final OnValidationListener onValidationListener) {
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
                    M m = (M) GsonUtil.jsonToObject(result, M.class);
                    switch (m.getCode()) {
                        case M.CODE_SUCCESS: {
                            onValidationListener.onSuccess();
                        }
                        break;
                        case M.CODE_FAILURE: {
                            onValidationListener.onFailure(m.getMessage());
                        }
                        break;
                        default:
                            break;
                    }
                }
            });
        }else{
            onValidationListener.onFailure("请输入正确的手机号码");
        }
    }

    @Override
    public void validateRegisterInformation(String phoneNumber,String userName, String password, final String rePassword, final OnValidationListener onValidationListener) {
        // TODO: 2017/10/22 信息校验
        //验证成功提交注册信息 并注册
        if(CommonUtil.isMobileNumber(userName)){
            onValidationListener.onFailure("不能使用手机号格式的用户名，请重新输入");
        }else if(TextUtils.isEmpty(userName)){
            onValidationListener.onFailure("用户名不能为空，请重新输入");
        }else if(TextUtils.isEmpty(password)){
            onValidationListener.onFailure("密码不能为空，请重新输入");
        }if(!rePassword.equals(password)&&!rePassword.isEmpty()){
            onValidationListener.onFailure("两次输入的密码不一致，请重新输入");
        }else{
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
                            onValidationListener.onSuccess();
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
        }

    }


}