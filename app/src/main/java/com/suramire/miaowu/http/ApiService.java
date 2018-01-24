package com.suramire.miaowu.http;

import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.http.base.BaseResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit 请求服务通用集合
 * 发送类请求使用POST 防止中文乱码
 */

public interface ApiService {

    public static final String JSON = "jsonString";
    /*用户相关*/
    //用户登录
    @FormUrlEncoded
    @POST("loginUser")
    Observable<BaseResponse<User>> login(@Field(JSON) String s);
    //获取单个用户信息
    @GET("getUser")
    Observable<BaseResponse<User>> getUser(@Query(JSON) String s);
    //检测手机号是否已被注册
    @GET("checkPhoneUser")
    Observable<BaseResponse<User>> checkPhoneUser(@Query(JSON) String s);
    //注册用户
    @FormUrlEncoded
    @POST("addUser")
    Observable<BaseResponse<User>> addUser(@Field(JSON) String s);


    @Multipart
    @POST("getPicUser")
    Observable<BaseResponse<Object>> uploadUserIcon(@Part("description") RequestBody description,
                                                   @Part MultipartBody.Part file);

    /*帖子相关*/
    //获取首页帖子 所有
    @FormUrlEncoded
    @POST("listMultiNote")
    Observable<BaseResponse<List<Multi>>> getMultiNotes(@Field(JSON) String string);

    //获取单个帖子的详情
    @GET("getDetailNote")
    Observable<BaseResponse<Note>> getDetailNote(@Query(JSON) String string);
    //发布新帖子
    @FormUrlEncoded
    @POST("addNote")
    Observable<BaseResponse<Object>> addNote(@Field(JSON) String string);
    //获取帖子所有配图
    @GET("getAllPictureNote")
    Observable<BaseResponse<List<String>>> getAllPictureNote(@Query(JSON) String string);

    //点赞操作
    @GET("thumbNote")
    Observable<BaseResponse<Object>> thumbNote(@Query(JSON) String string);

    //图片上传 单张
    @Multipart
    @POST("getPicNote")
    Observable<BaseResponse<Object>> uploadPicture(@Part("description") RequestBody description,
                                                   @Part MultipartBody.Part file);
    //发送保存配图路径
    @FormUrlEncoded
    @POST("picToDBNote")
    Observable<BaseResponse<Object>> picToDBNote(@Field(JSON) String string);

    /*回复相关*/
    //发表回复
    @FormUrlEncoded
    @POST("addReply")
    Observable<BaseResponse<Reply>> addReply(@Field(JSON) String string);

    //删除回复
    @GET("deleteReply")
    Observable<BaseResponse<Reply>> deleteReply(@Query(JSON) String string);

    //列出回复
    @GET("listReply")
    Observable<BaseResponse<List<Multi>>> listReply(@Query(JSON) String string);

    //获取楼中楼回复
    @GET("getFloorReply")
    Observable<BaseResponse<List<Multi>>> getFloorReply(@Query(JSON) String string);


}
