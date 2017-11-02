package com.suramire.miaowu.model;

import android.os.SystemClock;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.HTTPUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.suramire.miaowu.util.Constant.BASEURL;
import static com.suramire.miaowu.util.Constant.BASNOTEPICEURL;

/**
 * Created by Suramire on 2017/10/31.
 */

public class NoteDetailModel implements NoteDetailContract.Model {
    @Override
    public void getNoteDetail(final int noteId, final OnGetResultListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Note note = new Note();
                note.setId(noteId);
                HTTPUtil.getPost(BASEURL + "getDetailNote", note, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        try{
                            M m = (M) GsonUtil.jsonToObject(result, M.class);
                            SystemClock.sleep(1000);
                            switch (m.getCode()){
                                case M.CODE_SUCCESS:{
                                    Note note = (Note) GsonUtil.jsonToObject(m.getData(), Note.class);
                                    listener.onSuccess(note);
                                }break;
                                case M.CODE_FAILURE:{
                                    listener.onFailure(m.getMessage());
                                }break;
                                case M.CODE_ERROR:{
                                    listener.onError(m.getMessage());
                                }break;
                            }
                        }catch (Exception e){
                            listener.onError(e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void getPicture(final int noteId, final OnGetResultListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Note note = new Note();
                note.setId(noteId);
                HTTPUtil.getPost(BASEURL + "getAllPictureNote", note, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        try{
                            M m = (M) GsonUtil.jsonToObject(result, M.class);
                            switch (m.getCode()){
                                case M.CODE_SUCCESS:{
                                    List<String> strings = GsonUtil.jsonToList(m.getData(), String.class);
                                    List<String> target = new ArrayList<String>();
                                    for (String s1: strings ) {
                                        target.add(BASNOTEPICEURL+s1);
                                    }
                                    listener.onSuccess(target);
                                }break;
                                case M.CODE_FAILURE:{
                                    listener.onFailure(m.getMessage());
                                }break;
                                case M.CODE_ERROR:{
                                    listener.onError(m.getMessage());
                                }break;
                            }
                        }catch (Exception e){
                            listener.onError(e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
}
