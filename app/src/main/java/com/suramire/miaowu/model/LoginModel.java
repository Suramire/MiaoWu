package com.suramire.miaowu.model;

import android.text.TextUtils;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void doLogin(final String username, final String password, final OnGetResultListener onLoginListener) {
        //信息完整性验证
        if(TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
            onLoginListener.onFailure("请将帐号信息补充完整");
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = new User(null, username, password);
                    HTTPUtil.getPost(BASEURL + "loginUser", user , new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            onLoginListener.onError(e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            M m = (M) GsonUtil.jsonToObject(result, M.class);
                            switch (m.getCode()){
                                case M.CODE_SUCCESS:{
                                    User user = (User) GsonUtil.jsonToObject(m.getData(), User.class);
                                    onLoginListener.onSuccess(user);
                                }break;
                                case M.CODE_FAILURE:{
                                    onLoginListener.onFailure(m.getMessage());
                                }break;
                                case M.CODE_ERROR:{
                                    onLoginListener.onError(m.getMessage());
                                }break;
                            }
                        }
                    });
                }
            }).start();
        }

    }
}
