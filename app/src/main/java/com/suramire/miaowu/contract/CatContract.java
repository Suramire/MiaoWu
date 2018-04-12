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

        Observable<T> listCats();

    }

    interface View extends BaseView{
        Catinfo getCatInfo();

        int getCatId();

        void onAddCatSuccess(int cid);

        void onGetCatListSuccess(List<Catinfo> catinfos);

        List<String> getStringPaths();
    }

    interface Presenter extends BasePresenter<View>{
        void addCat();

        void getCat();

        void listCats();

        void uploadPicture();
    }
}
