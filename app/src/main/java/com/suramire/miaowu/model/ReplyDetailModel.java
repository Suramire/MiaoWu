package com.suramire.miaowu.model;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailModel implements ReplyDetailContract.Model {
    @Override
    public void getReplyList(final int floorid, final OnGetResultListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Reply reply = new Reply();
                reply.setFloorid(floorid);
                HTTPUtil.getPost(BASEURL + "getFloorReply", reply, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();

                        try {
                            M m = (M) GsonUtil.jsonToObject(result, M.class);
//                            L.e("result@replydetail: "+m.getData());
                            switch (m.getCode()) {
                                case M.CODE_SUCCESS: {
                                    if(m.getData()==null){
                                        listener.onSuccess(null);
                                    }else{
                                        List<Multi> multis = GsonUtil.jsonToList(m.getData(), Multi.class);
                                        L.e("multies.size():"+multis.size());
                                        listener.onSuccess(multis);
                                    }
                                }
                                break;
                                case M.CODE_FAILURE: {
                                    listener.onFailure(m.getMessage());
                                }
                                break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            listener.onError(e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void getUsersById(List<Integer> ids, OnGetResultListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                HTTPUtil.
            }
        }).start();
    }
}
