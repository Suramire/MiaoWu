package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.RegisterContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<User>  validatePhoneNumber(final String phoneNumber) {
        //判断手机号是否被注册
//        if(CommonUtil.isMobileNumber(phoneNumber)) {
////            请输入正确的手机号码
//        }
        User user = new User();
        user.setPhonenumber(phoneNumber);
        return ApiLoader.checkPhoneUser(user)
                .map(new ResponseFunc<User>());
    }

    @Override
    public Observable<User> validateRegisterInformation(String phoneNumber, String userName, String password, final String rePassword) {
        // 信息校验
        //验证成功提交注册信息 并注册
//        if(TextUtils.isEmpty(phoneNumber)|| TextUtils.isEmpty(password)|| TextUtils.isEmpty(rePassword)){
//            onValidationListener.onFailure("请将注册信息填写完整");
//        }else if(CommonUtil.isMobileNumber(userName)){
//            onValidationListener.onFailure("不能使用手机号格式的用户名，请重新输入");
//        }else if(rePassword.equals(password)){
            //转成json传送
            User user = new User(phoneNumber, userName, password,0);
        return ApiLoader.addUser(user)
                .map(new ResponseFunc<User>());

    }

}
