package com.suramire.miaowu.http;

import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Notification;
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
    //获取用户关注数
    @GET("getFollowCountUser")
    Observable<BaseResponse<Integer>> getUserFollowCount(@Query(JSON) String s);
    //获取用户关注用户信息
    @GET("getFollowUser")
    Observable<BaseResponse<List<User>>> getUserFollow(@Query(JSON) String s);
    //获取用户粉丝数
    @GET("getFollowerCountUser")
    Observable<BaseResponse<Integer>> getUserFollowerCount(@Query(JSON) String s);
    //获取用户粉丝用户信息
    @GET("getFollowerUser")
    Observable<BaseResponse<List<User>>> getUserFollower(@Query(JSON) String s);
    @GET("followUser")
    Observable<BaseResponse<Object>> followUser(@Query(JSON) String s);
    @GET("unfollowUser")
    Observable<BaseResponse<Object>> unfollowUser(@Query(JSON) String s);
    @GET("relationshipUser")
    Observable<BaseResponse<Integer>> getRelationship(@Query(JSON) String s);
    //更新用户信息
    @FormUrlEncoded
    @POST("updateUser")
    Observable<BaseResponse<User>> updateProfile(@Field(JSON) String string);
    @FormUrlEncoded
    @POST("searchUser")
    Observable<BaseResponse<List<User>>> searchUser(@Field(JSON) String string);
    //修改用户头像
    @Multipart
    @POST("getPicUser")
    Observable<BaseResponse<Object>> uploadUserIcon(@Part("description") RequestBody description,
                                                    @Part MultipartBody.Part file);
    //修改用户密码
    @GET("modifypwdUser")
    Observable<BaseResponse<Void>> modifyPassword(@Query(JSON) String string);

    /*帖子相关*/
    //获取首页帖子 所有
    @FormUrlEncoded
    @POST("listMultiNote")
    Observable<BaseResponse<List<Multi>>> getMultiNotes(@Field(JSON) String string);
    //列出所有未审核的帖子
    @GET("listunverifyNote")
    Observable<BaseResponse<List<Note>>> listUnverifyNotes();

    //搜索帖子
    @FormUrlEncoded
    @POST("searchNote")
    Observable<BaseResponse<List<Note>>> getSearchNotes(@Field(JSON) String string);
    //审核帖子
    @GET("passNote")
    Observable<BaseResponse<Void>> passNote(@Query(JSON) String string);
    @FormUrlEncoded
    @POST("unPassNote")
    Observable<BaseResponse<Void>> unPassNote(@Field(JSON) String string);
    //锁定帖子
    @GET("lockNote")
    Observable<BaseResponse<Void>> lockNote(@Query(JSON) String s);
    //解锁帖子
    @GET("unlockNote")
    Observable<BaseResponse<Void>> unlockNote(@Query(JSON) String s);
    //删除帖子
    @GET("deleteNote")
    Observable<BaseResponse<Void>> deleteNote(@Query(JSON) String string);
    //更新帖子
    @FormUrlEncoded
    @POST("updateNote")
    Observable<BaseResponse<Void>> updateNote(@Field(JSON) String string);

    //获取某用户发表的所有帖子信息
    @GET("getByUserNote")
    Observable<BaseResponse<List<Note>>> getNoteByUser(@Query(JSON) String string);

    //获取用户发帖数
    @GET("getuCountNote")
    Observable<BaseResponse<Integer>> getUserNoteCount(@Query(JSON) String string);

    //获取单个帖子的详情
    @GET("getDetailNote")
    Observable<BaseResponse<Note>> getDetailNote(@Query(JSON) String string);
    //发布新帖子 返回新帖子id
    @FormUrlEncoded
    @POST("addNote")
    Observable<BaseResponse<Integer>> addNote(@Field(JSON) String string);
    //获取帖子所有配图
    @GET("getAllPictureNote")
    Observable<BaseResponse<List<String>>> getAllPictureNote(@Query(JSON) String string);
    //获取帖子里的猫咪信息
    @GET("getCatNote")
    Observable<BaseResponse<Catinfo>> getCatNote(@Query(JSON) String string);

    //发布猫咪信息 返回 猫咪编号
    @FormUrlEncoded
    @POST("addCatNote")
    Observable<BaseResponse<Integer>> addCatNote(@Field(JSON) String string);

    //点赞操作
    @GET("thumbNote")
    Observable<BaseResponse<Object>> thumbNote(@Query(JSON) String string);

    //增加浏览次数
    @GET("increasecountNote")
    Observable<BaseResponse<Void>> increaseCount(@Query(JSON) String string);

    @FormUrlEncoded
    @POST("addApply")
    Observable<BaseResponse<Void>> addApply(@Field(JSON) String string);

    @GET("verifyApply")
    Observable<BaseResponse<Void>> verifyApply(@Query(JSON) String string);

    @GET("getoneApply")
    Observable<BaseResponse<Apply>> getOneApply(@Query(JSON) String string);

    @GET("getallApply")
    Observable<BaseResponse<List<Apply>>> getallApply(@Query(JSON) String string);

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

    //列出帖子页回复
    @GET("listmReply")
    Observable<BaseResponse<List<Multi0>>> listReply(@Query(JSON) String string);

    //列出回复详情
    @GET("listdetailReply")
    Observable<BaseResponse<List<Multi0>>> listReplyDetail(@Query(JSON) String string);

    /*通知相关*/

    //获取所有未读通知
    @GET("listunreadNotification")
    Observable<BaseResponse<List<Notification>>> listNotifications(@Query(JSON) String string);

    //阅读一条通知
    //返回已读的通知id
    @GET("readNotification")
    Observable<BaseResponse<Integer>> readNotification(@Query(JSON) String string);
    //获取已登录用户的未读通知数
    @GET("getunreadCountNotification")
    Observable<BaseResponse<Integer>> getunreadCount(@Query(JSON) String string);



}
