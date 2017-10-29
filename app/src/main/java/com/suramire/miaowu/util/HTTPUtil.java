package com.suramire.miaowu.util;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.suramire.miaowu.pojo.M;

import java.io.File;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Suramire on 2017/6/26.
 */

public class HTTPUtil {
    /**
     * post文件
     * @param url
     * @param file
     * @param callback
     * @return
     */
    public static Call getPost(String url, @NonNull File file, String fileName, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file );
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("picture", fileName, fileBody)
                .build();
        Request build = new Request.Builder().post(requestBody).url(url).build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(callback);
        return  call;
    }

    /**
     * post表单
     * @param url
     * @param
     * @param callback
     * @return
     */
    public static Call getPost(String url, @Nullable Object object, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        String userJson = GsonUtil.objectToJson(object);
        M m = new M(M.CODE_SUCCESS,userJson);
        String messageString = GsonUtil.objectToJson(m);
        formBuilder.add("jsonString",messageString);
        FormBody formBody = formBuilder.build();
        Request build = new Request.Builder().post(formBody).url(url).build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(callback);
        return  call;
    }

}
