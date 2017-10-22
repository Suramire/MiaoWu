package com.suramire.miaowu.model;

import com.suramire.miaowu.listener.OnLoginListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

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
                HTTPUtil.getPost(BASEURL + "loginUser", map, new Callback() {
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
                                onLoginListener.onSuccess(m.getData());
                            }break;
                            case M.CODE_FAILURE:{
                                onLoginListener.onFailure(m.getMessage());
                            }break;
                            default:{
                                    onLoginListener.onError(m.getMessage());
                            }break;

                        }
                    }
                });
            }
        }).start();
    }
}
