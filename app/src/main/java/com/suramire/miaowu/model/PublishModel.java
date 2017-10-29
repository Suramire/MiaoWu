package com.suramire.miaowu.model;

import android.text.TextUtils;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.pojo.M;
import com.suramire.miaowu.pojo.Note;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.SPUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/29.
 */

public class PublishModel {
    public void publish(String title, String content, List<String> pictues, final OnGetResultListener listener){
        // TODO: 2017/10/29 获取当前用户的uid
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
            Note note = new Note(uid,title,content,now);
            HTTPUtil.getPost(BASEURL + "addNote", note, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    L.e("result@publish"+result);
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
        }
    }
}
