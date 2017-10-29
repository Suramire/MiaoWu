package com.suramire.miaowu.model;

import android.os.SystemClock;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/29.
 */

public class HomeModel {
    public void getData(final OnGetResultListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTPUtil.getPost(BASEURL + "listNote",null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onError(e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        try{
                            M m = (M) GsonUtil.jsonToObject(result, M.class);
                            SystemClock.sleep(3000);
                            switch (m.getCode()){
                                case M.CODE_SUCCESS:{
                                    listener.onSuccess(m.getData());
                                }break;
                                case M.CODE_FAILURE:{
                                    listener.onFailure(m.getMessage());
                                }break;
                                case M.CODE_ERROR:{
                                    listener.onError(m.getMessage());
                                }break;
                            }
                        }catch (Exception e){
                            listener.onError(e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
}
