package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;

import java.util.List;

import rx.Observable;


public interface CatContract {

    interface Model<T>{
        Observable<T> addCat(Catinfo catinfo);

        Observable<T> getCat(int catId);


        Observable<T> getAllPictures(int catId);

    }

    interface View extends BaseView{
        Catinfo getCatInfo();

        int getCatId();

        void onAddCatSuccess(int cid);


        List<String> getStringPaths();

        void onGetAllPicturesSuccess(List<String> paths);
    }

    interface Presenter extends BasePresenter<View>{
        void addCat();

        void getCat();

        void uploadPicture();

        void getAllPictures();
    }
}
