package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;
import com.suramire.miaowu.util.SPUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public class ProfileModel implements ProfileContract.Model {
    @Override
    public Observable<User> getProfile(int id) {
        int uid = (int) SPUtils.get("uid", 0);
        User tUser = new User();
        tUser.setId(uid);
        return ApiLoader.getUser(tUser)
                .map(new ResponseFunc<User>());
    }

    @Override
    public Observable<User> updateProfile(User user) {
        return ApiLoader.updateProfile(user)
                .map(new ResponseFunc<User>());
    }

    @Override
    public Observable<Void> updateAvater(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.updateAvater(user)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<Void> uploadAvater(int uid,String path) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", uid+".png", requestFile);
        String descriptionString = "hello, 这是文件描述";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        return ApiLoader.uploadAvater(description,body)
                .map(new ResponseFunc<Void>());
    }


}
