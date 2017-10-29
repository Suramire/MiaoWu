package com.suramire.miaowu.model;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.pojo.User;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.SPUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/25.
 */

public class ProfileModel {

    public void getProfile(int id, final OnGetResultListener listener) {
        int uid = (int) SPUtils.get("uid", 0);
        User tUser = new User();
        tUser.setId(uid);
        if (uid > 0) {
            //已登录
            HTTPUtil.getPost(BASEURL + "getUser", tUser, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    try {
                        M m = (M) GsonUtil.jsonToObject(json, M.class);
                        listener.onSuccess(GsonUtil.jsonToObject(m.getData(), User.class));
                    } catch (Exception e) {
                        listener.onError(e.getMessage());
                    }
                }
            });
        } else {
            listener.onFailure("检查是否登录");
        }
    }
}
