package com.suramire.miaowu.http;

import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Follow;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.http.base.BaseResponse;
import com.suramire.miaowu.http.base.RetrofitServiceManager;
import com.suramire.miaowu.util.GsonUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Suramire on 2018/1/23.
 */

public class ApiLoader {

    public  static ApiService getApiService(){
        return SingletonHolder.API_SERVICE;
    }
    private static class SingletonHolder{
        private static final ApiService API_SERVICE = RetrofitServiceManager.getInstance().create(ApiService.class);
    }

    protected static  <T> Observable<T> observer(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*用户相关*/
    public static Observable<BaseResponse<User>> login(User user){
        return observer(getApiService().login(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<User>> getUser(User user){
        return observer(getApiService().getUser(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<User>> checkPhoneUser(User user){
        return observer(getApiService().checkPhoneUser(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<User>> addUser(User user){
        return observer(getApiService().addUser(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<Object>> uploadUserIcon(RequestBody description , MultipartBody.Part body){
        return observer(getApiService().uploadUserIcon(description,body));
    }

    public static Observable<BaseResponse<Integer>> getUserFollowCount(User user){
        return observer(getApiService().getUserFollowCount(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<Integer>> getUserFollowerCount(User user){
        return observer(getApiService().getUserFollowerCount(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<List<User>>> getUserFollow(User user){
        return observer(getApiService().getUserFollow(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<Object>> followUser(Follow follow){
        return observer(getApiService().followUser(GsonUtil.objectToJson(follow)));
    }


    public static Observable<BaseResponse<Object>> unfollowUser(Follow follow){
        return observer(getApiService().unfollowUser(GsonUtil.objectToJson(follow)));
    }

    public static Observable<BaseResponse<Integer>> getRelationship(Follow follow){
        return observer(getApiService().getRelationship(GsonUtil.objectToJson(follow)));
    }

    public static Observable<BaseResponse<User>> updateProfile(User user){
        return observer(getApiService().updateProfile(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<List<User>>> searchUser(User user){
        return observer(getApiService().searchUser(GsonUtil.objectToJson(user)));
    }


    public static Observable<BaseResponse<List<User>>> getUserFollower(User user){
        return observer(getApiService().getUserFollower(GsonUtil.objectToJson(user)));
    }


    /*帖子相关*/

    public static Observable<BaseResponse<List<Multi>>> getMultiNotes(Note note){
        return observer(getApiService().getMultiNotes(GsonUtil.objectToJson(note)));
    }
    public static Observable<BaseResponse<List<Note>>> getNoteByUser(User user){
        return observer(getApiService().getNoteByUser(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<List<Note>>> searchNote(Note note){
        return observer(getApiService().getSearchNotes(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<List<Note>>> listUnverifyNotes(){
        return observer(getApiService().listUnverifyNotes());
    }

    public static Observable<BaseResponse<Void>> passNote(Note note){
        return observer(getApiService().passNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> unPassNote(Note note){
        return observer(getApiService().unPassNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> lockNote(Note note){
        return observer(getApiService().lockNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> unlockNote(Note note){
        return observer(getApiService().unlockNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> deleteNote(Note note){
        return observer(getApiService().deleteNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> updateNote(Note note){
        return observer(getApiService().updateNote(GsonUtil.objectToJson(note)));
    }



    public static Observable<BaseResponse<Note>> getDetailNote(Note note){
        return observer(getApiService().getDetailNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Integer>> addCat(Catinfo catinfo){
        return observer(getApiService().addCatNote(GsonUtil.objectToJson(catinfo)));
    }

    public static Observable<BaseResponse<Integer>> addNote(Note note){
        return observer(getApiService().addNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<List<String>>> getAllPictureNote(Note note){
        return observer(getApiService().getAllPictureNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Catinfo>> getCat(Note note){
        return observer(getApiService().getCatNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Object>> thumbNote(Note note){
        return observer(getApiService().thumbNote(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> increaseCount(Note note){
        return observer(getApiService().increaseCount(GsonUtil.objectToJson(note)));
    }

    public static Observable<BaseResponse<Void>> addApply(Apply apply){
        return observer(getApiService().addApply(GsonUtil.objectToJson(apply)));
    }

    public static Observable<BaseResponse<Void>> verifyApply(Apply apply){
        return observer(getApiService().verifyApply(GsonUtil.objectToJson(apply)));
    }

    public static Observable<BaseResponse<Apply>> getOneApply(Apply apply){
        return observer(getApiService().getOneApply(GsonUtil.objectToJson(apply)));
    }

    public static Observable<BaseResponse<Object>> uploadPicture(RequestBody description , MultipartBody.Part body){
        return observer(getApiService().uploadPicture(description,body));
    }

    public static Observable<BaseResponse<Object>> picToDBNote(List<HashMap<String, String>> maps){
        return observer(getApiService().picToDBNote(GsonUtil.objectToJson(maps)));
    }

    public static Observable<BaseResponse<Integer>> getUserNoteCount(User user){
        return observer(getApiService().getUserNoteCount(GsonUtil.objectToJson(user)));
    }


    /*回复相关*/
    public static Observable<BaseResponse<Reply>> addReply(Reply reply){
        return observer(getApiService().addReply(GsonUtil.objectToJson(reply)));
    }

    public static Observable<BaseResponse<Reply>> deleteReply(Reply reply){
        return observer(getApiService().deleteReply(GsonUtil.objectToJson(reply)));
    }

    public static Observable<BaseResponse<List<Multi0>>> listReplys(Reply reply){
        return observer(getApiService().listReply(GsonUtil.objectToJson(reply)));
    }

    public static Observable<BaseResponse<List<Multi0>>> listReplyDetail(Reply reply){
        return observer(getApiService().listReplyDetail(GsonUtil.objectToJson(reply)));
    }

    /*通知相关*/

    public static Observable<BaseResponse<List<Notification>>> listNotifications(User user){
        return observer(getApiService().listNotifications(GsonUtil.objectToJson(user)));
    }

    public static Observable<BaseResponse<Integer>> readNotification(Notification notification){
        return observer(getApiService().readNotification(GsonUtil.objectToJson(notification)));
    }

    public static Observable<BaseResponse<Integer>> getunreadCount(User user){
        return observer(getApiService().getunreadCount(GsonUtil.objectToJson(user)));
    }



}
