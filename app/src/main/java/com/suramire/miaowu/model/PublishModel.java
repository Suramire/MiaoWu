package com.suramire.miaowu.model;

import android.text.TextUtils;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.SPUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/29.
 */

public class PublishModel implements PublishContract.Model {
    @Override
    public void publish(String title, String content, final List<String> pictues, final OnGetResultListener listener){
        int uid = (int) SPUtils.get("uid", 0);
        if(uid==0){
            listener.onFailure("请先登录");
        }else if(TextUtils.isEmpty(title)){
            listener.onFailure("标题不能为空");
        }else if(TextUtils.isEmpty(content)){
            listener.onFailure("内容不能为空");
        }else if(pictues==null){
            listener.onFailure("请至少附带一张配图");
        }else{
            Timestamp now = CommonUtil.getTimeStamp();
            final Note note = new Note(uid,title,content,now);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HTTPUtil.getPost(BASEURL + "addNote", note, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onError(e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            try {
                                M m = (M) GsonUtil.jsonToObject(result, M.class);
                                switch (m.getCode()){
                                    case M.CODE_SUCCESS:{
                                        listener.onSuccess(m.getData());
                                    }break;
                                    case M.CODE_FAILURE:{
                                        listener.onFailure(m.getMessage());
                                    }break;
                                    case M.CODE_ERROR:{
                                        listener.onError(m.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                                listener.onError(e.getMessage());
                            }
                        }
                    });
                }
            }).start();

        }
    }

    @Override
    public void uploadPicture(Object object, List<String> pictues, final OnGetResultListener listener){
        int nid = Integer.parseInt(object.toString());
        List<HashMap<String,String>> names = new ArrayList<>();
        for (int i =0;i<pictues.size();i++) {
//            if(pictues.size()<2){
//
//            }
            File file = new File(pictues.get(i));
            HTTPUtil.getPost(BASEURL + "getPicNote", file, nid + "_" + i + ".png", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    try {
                        M m = (M) GsonUtil.jsonToObject(result, M.class);
                        switch (m.getCode()){
                            case M.CODE_SUCCESS:{
                                listener.onSuccess(null);
                            }break;
                            case M.CODE_FAILURE:{
                                listener.onFailure(m.getMessage());
                            }break;
                            case M.CODE_ERROR:{
                                listener.onError(m.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        listener.onError(e.getMessage());
                    }
                }
            });
            HashMap<String,String> map = new HashMap<>();
            map.put("nid",nid+"");
            map.put("picname",nid+"_"+i+".png");
            names.add(map);
        }
        //上传图片
        // TODO: 2017/10/29 对要上传的图片进行压缩处理
        HTTPUtil.getPost(BASEURL + "picToDBNote", names, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
