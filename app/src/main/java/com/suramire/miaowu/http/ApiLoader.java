package com.suramire.miaowu.http;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
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

    /*帖子相关*/

    public static Observable<BaseResponse<List<Multi>>> getMultiNotes(){
        Note note = new Note();
        note.setId(23);
        note.setTitle("this is title");
        return observer(getApiService().getMultiNotes(GsonUtil.objectToJson(note)));
    }
    public static Observable<BaseResponse<List<Note>>> getNoteByUser(User user){
        return observer(getApiService().getNoteByUser(GsonUtil.objectToJson(user)));
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





}
