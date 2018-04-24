package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;
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

public class CatModel implements CatContract.Model {

    private OnResultListener listener;

    public void setListener(OnResultListener listener) {
        this.listener = listener;
    }

    int index = 0;

    @Override
    public Observable<Integer> addCat(Catinfo catinfo) {
        return ApiLoader.addOneCat(catinfo)
                .map(new ResponseFunc<Integer>());
    }

    @Override
    public Observable<Catinfo> getCat(int catId) {
        Catinfo catinfo = new Catinfo();
        catinfo.setId(catId);
        return ApiLoader.getOneCat(catinfo)
                .map(new ResponseFunc<Catinfo>());
    }


    @Override
    public Observable<List<String>> getAllPictures(int catId) {
        Catinfo catinfo = new Catinfo();
        catinfo.setId(catId);
        return ApiLoader.getAllpictuesCat(catinfo)
                .map(new ResponseFunc<List<String>>());
    }

    @Override
    public Observable<Void> applyCat(int catId, int uid) {
        Catinfo catinfo = new Catinfo();
        catinfo.setId(catId);
        catinfo.setUid(uid);
        return ApiLoader.applyCat(catinfo)
                .map(new ResponseFunc<Void>());
    }


    public void uploadPicture(final List<String> paths, final int cid){
        if(paths==null || paths.size()==0){
        }else{
            upload(paths, cid, 0);
        }

    }

    @Override
    public Observable<List<M>> listAppliedCat() {
        return ApiLoader.listApplied()
                .map(new ResponseFunc<List<M>>());
    }

    @Override
    public Observable<Void> uploadPicturePaths(int cid, List paths) {
        List<HashMap<String,String>> names = new ArrayList<>();
        for (int i =0;i<paths.size();i++) {
            HashMap<String,String> map = new HashMap<>();
            map.put("cid",cid+"");
            map.put("picname",cid+"_"+i+".png");
            names.add(map);
        }
        return ApiLoader.picToDBCat(names)
                .map(new ResponseFunc<Void>());
    }


    /**
     * 上传图片
     * @param paths
     * @param cid
     * @param i
     */
    private void upload(final List<String> paths, final int cid, final int i) {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(paths.get(i)));
        final MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", cid + "_" + i + ".png", requestFile);
        String descriptionString = "hello, 这是文件描述";
        final RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        ApiLoader.uploadCatPicture(description, body)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if(i<paths.size()-1){
                            upload(paths,cid,i+1);
                        }else{
                            if(null !=listener){
                                listener.onSuccess(null);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        L.e("上传图片失败:" + throwable.getMessage());
//                        if(null !=listener){
//                            listener.onError("上传图片失败");
//                        }
                    }
                });
    }
}
