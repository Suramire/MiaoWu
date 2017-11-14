package com.suramire.miaowu.presenter;

import android.os.Handler;

import com.suramire.miaowu.base.OnGetResultListener;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.model.NoteDetailModel;

/**
 * Created by Suramire on 2017/10/31.
 */

public class NoteDetailPresenter implements NoteDetailContract.Presenter {
    private final NoteDetailModel mNoteDetailModel;
    private final NoteDetailContract.View mView;
    private final Handler mHandler;

    public NoteDetailPresenter(NoteDetailContract.View view) {
        mView = view;
        mNoteDetailModel = new NoteDetailModel();
        mHandler = new Handler();
    }

//    @Override
//    public void getData() {
//        mView.showLoading();
//        mNoteDetailModel.getNoteDetail(mView.getNoteId(), new OnGetResultListener() {
//            @Override
//            public void onSuccess(final Object object) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.cancelLoading();
//                        mView.onGetSuccess((Note)object);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(final String failureMessage) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.cancelLoading();
//                        mView.onGetFailure(failureMessage);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(final String errorMessage) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.cancelLoading();
//                        mView.onGetError(errorMessage);
//                    }
//                });
//            }
//        });
//
//    }

//    @Override
//    public void getPicture() {
//        mNoteDetailModel.getPicture(mView.getNoteId(), new OnGetResultListener() {
//            @Override
//            public void onSuccess(final Object object) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.onGetPictureSuccess((List<String>) object);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(final String failureMessage) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.onGetPictureFailure(failureMessage);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(final String errorMessage) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.onGetPictureError(errorMessage);
//                    }
//                });
//            }
//        });
//    }

    @Override
    public void getReply() {
        mNoteDetailModel.getNoteReply(mView.getNoteId(), new OnGetResultListener() {
            @Override
            public void onSuccess(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onGetReplySuccess(object);
                    }
                });
            }

            @Override
            public void onFailure(final String failureMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onGetReplyFailure(failureMessage);
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onGetReplyError(errorMessage);
                    }
                });
            }
        });
    }

    @Override
    public void thumb() {
        mNoteDetailModel.thumb(mView.getNoteId(), new OnGetResultListener() {
            @Override
            public void onSuccess(Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onThumbSuccess();
                    }
                });
            }

            @Override
            public void onFailure(String failureMessage) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }
}
