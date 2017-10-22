package com.suramire.miaowu.model;

import android.os.Looper;

import com.suramire.miaowu.listener.OnEventCodeListener;
import com.suramire.miaowu.listener.OnValidationListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.util.Constant;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Suramire on 2017/10/22.
 */

public class RegisterModel implements IRegisterModel {


    private EventHandler mEventHandler;

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RegisterModel() {


    }

    @Override
    public void validatePhoneNumber(final String phoneNumber, final OnValidationListener onValidationListener) {
        //判断手机号是否被注册
        //
        // TODO: 2017/10/22 手机号码格式判断
        HashMap<String, String> map = new HashMap<>();
        map.put("phonenumber", phoneNumber);
        HTTPUtil.getPost(Constant.BASEURL + "checkPhoneUser", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onValidationListener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                M m = (M) GsonUtil.jsonToObject(result, M.class);
                switch (m.getCode()){
                    case M.CODE_SUCCESS:{
                        onValidationListener.onSuccess();
                        SMSSDK.getVerificationCode("86", phoneNumber);
                        setPhoneNumber(phoneNumber);
                    }break;
                    case M.CODE_FAILURE:{
                        onValidationListener.onFailure(m.getMessage());
                    }break;
                    default:break;
                }


            }
        });

    }

    @Override
    public void validateValidationNumber(String validataionNumber, OnValidationListener onValidationListener) {
        SMSSDK.submitVerificationCode("86",getPhoneNumber(),validataionNumber);
        //todo 验证码验证
        if(true){
            onValidationListener.onSuccess();
        }
    }

    @Override
    public void validateRegisterInformation(String phoneNumber,String userName, String password, final String rePassword, final OnValidationListener onValidationListener) {
        // TODO: 2017/10/22 信息校验
        //验证成功提交注册信息 并注册
        String json = GsonUtil.objectToJson(new User(phoneNumber, userName, password));
        //转成json传送
        HashMap<String,String> map = new HashMap<>();
        map.put("json", json);
        HTTPUtil.getPost(Constant.BASEURL + "addUser", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onValidationListener.onFailure(e.getMessage());
            }

            @Override
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

    @Override
    public void registerEventHandler(final OnEventCodeListener onEventCodeListener) {
        mEventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    onEventCodeListener.onEventError(msg);
                    L.e("error:" + msg);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(App.getApp(), "错误："+msg, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    // {"status":468,"detail":"需要校验的验证码错误"}
                    // 短信上限 {"status":477,"detail":"当前手机号发送短信的数量超过限额"}

                    //验证码错误
                } else {
                    onEventCodeListener.onEventCode(event);

                    L.e("event:" + event);
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {


                    }
                    else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
//


                    }
                }
            }
        };
        // 注册监听器
        Looper.prepare();
        SMSSDK.registerEventHandler(mEventHandler);
        Looper.loop();
    }

    @Override
    public void unregisterEventHandler() {
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}
