package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.OnResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Suramire on 2017/10/29.
 */

public class PublishModel implements PublishContract.Model {


    private OnResultListener listener;

    public void setListener(OnResultListener listener) {
        this.listener = listener;
    }


    @Override
    public Observable<Object> uploadPicturePath(Object object, List pictues) {
        final int nid = Integer.parseInt(object.toString());
        List<HashMap<String,String>> names = new ArrayList<>();
        for (int i =0;i<pictues.size();i++) {
            HashMap<String,String> map = new HashMap<>();
            map.put("nid",nid+"");
            map.put("picname",nid+"_"+i+".png");
            names.add(map);
        }
        return ApiLoader.picToDBNote(names).
                map(new ResponseFunc<Object>());
    }



    @Override
    public Observable<Integer> publishNoteInfo(Note note) {
        note.setPublish(CommonUtil.getTimeStamp());//设置发帖时间
        note.setThumbs(0);//初始化点赞数为0
        note.setViewcount(0);//初始化浏览数为0
        note.setVerified(0);//新帖默认为待审核状态
        return ApiLoader.addNote(note)//发帖成功后返回的是新帖子的编号
                .map(new ResponseFunc<Integer>());
    }

    public void uploadPicture(final List<String> pictues, final int index, final int max, final int nid) {
        if(index<=max-1){
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(pictues.get(index)));
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", nid + "_" + index + ".png", requestFile);
            String descriptionString = "hello, 这是文件描述";
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString);
            ApiLoader.uploadPicture(description, body)
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            uploadPicture(pictues, index+1,max,nid);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            L.e("上传图片失败:" + throwable.getMessage());
                        }
                    });
        }else{
            if(null != listener){
                listener.onSuccess(null);
            }
        }
    }
}
