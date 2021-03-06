package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;

import java.util.List;

import rx.Observable;


public interface CatContract {

    interface Model<T>{
        Observable<T> addCat(Catinfo catinfo);

        Observable<T> getCat(int catId);

        Observable<T> getAllPictures(int catId);

        Observable<T> applyCat(int catId, int uid);

        Observable<T> listAppliedCat();

        Observable<T> uploadPicturePaths(int cid,List<String> paths);

    }

    interface View extends BaseView{
        Catinfo getCatInfo();

        int getCatId();

        int getUid();

        void onAddCatSuccess(int cid);

        void onApplyCatSuccess();

        List<String> getStringPaths();

        void onUploadCatPicturesSuccess();

        void onGetAllPicturesSuccess(List<String> paths);

        void onListAppliedCatSuccess(List<M> catinfo);
    }

    interface Presenter extends BasePresenter<View>{
        //添加一只猫
        void addCat();

        //获取一只猫的信息
        void getCat();

        //上传猫咪配图的文件名

        void uploadPictuePaths();

        //上传猫的图片
        void uploadPicture();

        //获取猫的图片
        void getAllPictures();

        //申请领养
        void applyCat();

        //列出待审核的领养申请
        void listAppliedCat();


    }
}
