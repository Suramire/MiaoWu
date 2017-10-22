package com.suramire.miaowu.model;

import com.suramire.miaowu.listener.OnLoginListener;
import com.suramire.miaowu.util.Constant;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Suramire on 2017/10/21.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void doLogin(final String username, final String password, final OnLoginListener onLoginListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", username);
                map.put("password", password);
                HTTPUtil.getPost(Constant.BASEURL + "loginUser", map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        L.e("onFailure"+e.getMessage());
                        onLoginListener.onError();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        L.e(result);
                        switch (result){
                            case "success":{
                                onLoginListener.onSuccess();
                            }break;
                            case "failure":{
                                onLoginListener.onFailure();
                            }break;
                            default:{
                                onLoginListener.onError();
                            }break;
                        }
                    }
                });
            }
        }).start();
    }
}
