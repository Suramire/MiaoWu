package com.suramire.miaowu.ui;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.adapter.MultiItemAdapter;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.fragment.BottomCommentDialogFragment;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/17.
 */

public class NoteDetailActivity extends BaseActivity implements NoteDetailContract.View {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list_notedetail)
    ListView mListNotedetail;
    @Bind(R.id.bar_num)
    TextView mBarNum;
    @Bind(R.id.bar_num2)
    TextView mBarNum2;
    private ProgressDialog mProgressDialog;
    private NoteDetailPresenter mNoteDetailPresenter;
    private int mNoteId;
    private MultiItemAdapter mAdapter;
    private List<Object> mObjects;
    private boolean thumbed;
    private Integer mThumbs;
    private  int mReplyCount;
    private boolean isAtComment;//是否在评论页标志位
    private int index;//上次浏览的index
    private int top;//距离顶部距离

    @Override
    public int bindLayout() {
        return R.layout.activity_notedetail;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        mNoteId = getIntent().getIntExtra("noteId", 0);
        Multi multi = (Multi) getIntent().getSerializableExtra("multi");
        //查询帖子信息
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在读取帖子信息，请稍候……");
        mNoteDetailPresenter = new NoteDetailPresenter(this);
//        mNoteDetailPresenter.getData();
        // TODO: 2017/11/14 多图片上传完整性问题
        // TODO: 2017/11/14 点击详情页 浏览次数+1
        mObjects = new ArrayList<>();
        List<String> picturesStrings = multi.getPicturesStrings();
        mObjects.add(picturesStrings);
        mObjects.add(multi.getmUser());
        mObjects.add(multi.getmNote());
        mAdapter = new MultiItemAdapter(this, mObjects);
        mListNotedetail.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mThumbs = multi.getmNote().getThumbs();
        thumb(mThumbs);
        mNoteDetailPresenter.getReply();

    }


    @Override
    protected String getTitleString() {
        return "帖子详情";
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.cancel();
    }

    @Override
    public int getNoteId() {
        return mNoteId;
    }



    private void thumb(int count){
        if (count <= 0) {
            mBarNum2.setVisibility(View.GONE);
        } else {
            if (count > 99) {
                mBarNum2.setText("99+");
            } else {
                mBarNum2.setText(count + "");
            }
        }
    }

    private void setReplyCount(int count){
        if (count <= 0) {
            mBarNum.setVisibility(View.GONE);
        } else {
            if (count > 99) {
                mBarNum.setText("99+");
            } else {
                mBarNum.setText(count + "");
            }
        }
    }




    @Override
    public void onGetReplySuccess(Object object) {
        List<Multi> replies = (List<Multi>) object;
        for (Multi multi : replies
                ) {
            mObjects.add(multi);
            //统计楼层数（非楼层回复）
            if(multi.getmReply().getReplyuid()==0){
                mReplyCount++;
            }
        }
        mAdapter.notifyDataSetChanged();
        setReplyCount(mReplyCount);

    }

    @Override
    public void onGetReplyFailure(String failureMessage) {
        setReplyCount(0);
    }

    @Override
    public void onGetReplyError(String errorMessage) {
        setReplyCount(0);
    }

    @Override
    public void onThumbSuccess() {
        thumbed = true;
        thumb(mThumbs+1);
    }


    @OnClick({R.id.btn_comment, R.id.btn_like, R.id.btn_share, R.id.editText3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment: {
                if(!isAtComment){
                    isAtComment = true;
                    //记录上次浏览位置
                    index = mListNotedetail.getFirstVisiblePosition();
                    L.e("index:" + index);
                    View v = mListNotedetail.getChildAt(0);
                    top = (v == null) ? 0 : v.getTop();
                    L.e("top:"+ top);
                    mListNotedetail.smoothScrollToPosition(2);
                }else{
                    isAtComment = false;
                    L.e("index2:" + index);
                    L.e("top2:"+ top);
                    mListNotedetail.setSelectionFromTop(index, top);
                }

            }
            break;
            case R.id.btn_like:
                if(!thumbed){
                    mNoteDetailPresenter.thumb();
                }
                break;
            case R.id.btn_share:
                break;
            case R.id.editText3: {
                if (!CommonUtil.isLogined()) {
                    Snackbar.make(findViewById(android.R.id.content), "您还未登录", Snackbar.LENGTH_INDEFINITE)
                            .setAction("登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(LoginActivity.class);
                                }
                            }).show();
                } else {
                    final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                    final BottomCommentDialogFragment bottomCommentDialogFragment = BottomCommentDialogFragment.newInstance();
                    bottomCommentDialogFragment.setReplyListener(new BottomCommentDialogFragment.OnReplyListener() {
                        @Override
                        public void onSucess() {
                            Toast.makeText(mContext, "发表成功！", Toast.LENGTH_SHORT).show();
                            bottomCommentDialogFragment.dismiss();
                        }

                        @Override
                        public void onFailure(String failureMessage) {
                            Toast.makeText(mContext, "发表失败:" + failureMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(mContext, "发表过程出现错误:" + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putInt("nid", getNoteId());
                    bottomCommentDialogFragment.setArguments(bundle);
                    bottomCommentDialogFragment.show(mFragTransaction, "111");
                }
            }
            break;

        }
    }

}
