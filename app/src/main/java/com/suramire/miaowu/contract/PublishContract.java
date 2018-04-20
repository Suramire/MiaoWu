package com.suramire.miaowu.contract;

import com.suramire.miaowu.base.BasePresenter;
import com.suramire.miaowu.base.BaseView;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Note;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/29.
 */

public interface PublishContract {
    interface Model<T> {
        /**
         * 发送猫咪信息
         * @param catinfo
         * @return 猫咪编号
         */
        Observable<T> publishCatInfo(Catinfo catinfo);

        /**
         * 发布帖子信息
         * @param note 帖子信息
         */
        Observable<T> publishNoteInfo(Note note);

        /**
         * 上传帖子图片文件名
         * @param object 帖子编号 需强转int
         * @param pictues 图片名数组
         * @return
         */

        Observable<T> uploadPicturePath(Object object, List<String> pictues);

    }

    interface View extends BaseView {

        //返回新帖子信息
        Note getNoteInfo();
        //返回猫咪信息
        Catinfo getCatinfo();

        void onPostNoteSuccess(int nid);

        void onUploadPicturesSuccess();
        //帖子配图的路径
        List<String> getPicturePaths();
    }

    interface Presenter extends BasePresenter<View> {
        //发送帖子信息
        void publishNote();
        //发送帖子配图文件序号名
        void publishPicturePaths(int nid);
    }
}
