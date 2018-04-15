package com.suramire.miaowu.ui;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.adapter.MultiItemAdapter;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.ui.dialog.BottomCommentDialogFragment;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/17.
 */

public class NoteDetailActivity extends BaseActivity<NoteDetailPresenter> implements NoteDetailContract.View {

    @Bind(R.id.toolbar)
    MyToolbar mToolbar;
    @Bind(R.id.list_notedetail)
    ListView mListNotedetail;
    @Bind(R.id.bar_num)
    TextView mBarNum;
    @Bind(R.id.bar_num2)
    TextView mBarNum2;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.edittext_comment)
    EditText edittextComment;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.ll_popup)
    LinearLayout llPopup;
    @Bind(R.id.ll_bottomadmin)
    LinearLayout llBottomadmin;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    private ProgressDialog mProgressDialog;
    private int noteId;
    private MultiItemAdapter mAdapter;
    private List<Object> mObjects;
    private boolean thumbed;
    private Integer mThumbs;
    private int mReplyCount;
    private boolean isAtComment;//是否在评论页标志位
    private int index;//上次浏览的index
    private int top;//距离顶部距离
    private int userId;
    private int verify;
    private Integer verified;
    private Note mNote;
    private User mUser;
    private Catinfo mCatInfo;


    @Override
    public int bindLayout() {
        return R.layout.activity_notedetail;
    }

    @Override
    public void createPresenter() {
        mPresenter = new NoteDetailPresenter();
    }

    @Override
    public void initView() {
        mToolbar.setStyle(MyToolbar.STYLE_LEFT_AND_TITLE);
        mToolbar.setLeftImage(R.drawable.ic_arrow_back_blue);
        mToolbar.setRightText("帖子选项(长按)");
        registerForContextMenu(toolbarTextRight);
        mToolbar.setLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ApiConfig.RESULTCODE_NOTIFICATION);
                finish();
            }
        });
        noteId = getIntent().getIntExtra("noteId", 0);
        userId = getIntent().getIntExtra("userId", 0);
        verify = getIntent().getIntExtra("verifyApply", 0);


        //查询帖子信息
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在读取帖子信息，请稍候……");

        mObjects = new ArrayList<>();

        mAdapter = new MultiItemAdapter(this, mObjects);
        mListNotedetail.setAdapter(mAdapter);
        mPresenter.getPictue();

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
    public void onSuccess(Object object) {
        List<Multi0> replies = (List<Multi0>) object;
        for (Multi0 multi : replies
                ) {
            mObjects.add(multi);
            //统计楼层数（非楼层回复）
            if (multi.getReply().getReplyuid() == 0) {
                mReplyCount++;
            }
        }

        mAdapter.notifyDataSetChanged();
        setReplyCount(mReplyCount);
        //成功获取帖子信息后使帖子的阅读量加一
        mPresenter.increaseNouteCount();
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public int getNoteId() {
        return noteId;
    }


    private void thumb(int count) {

        if (count <= 0) {
            mBarNum2.setVisibility(View.GONE);
        } else {
            mBarNum2.setVisibility(View.VISIBLE);
            if (count > 99) {
                mBarNum2.setText("99+");
            } else {
                mBarNum2.setText(count + "");
            }
        }
    }

    private void setReplyCount(int count) {
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
    public void onThumbSuccess() {
        thumbed = true;
        ToastUtil.showShortToastCenter("点赞成功！");
        thumb(mThumbs + 1);
    }

    @Override
    public void onGetNoteInfoSuccess(Note note) {
//        mNote = note;
        verified = note.getVerified();
//        /*将帖子类型存放于用户密码字段
//        因为用户信息先于帖子信息显示，
//        显示用户信息前需根据帖子类型隐藏或显示申请按钮*/
//        mUser.setPassword(note.getType()+"");
//        mObjects.add(mUser);
        mObjects.add(note);
        mAdapter.notifyDataSetChanged();
        mThumbs = note.getThumbs();
        thumb(mThumbs);
        if (note.getVerified() == 0) {
            //显示审核按钮
            llBottomadmin.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
        }


    }

    @Override
    public void onGetUserInfoSuccess(User user) {
//        mUser = user;
        mObjects.add(user);
        mAdapter.notifyDataSetChanged();
        //帖子是登录用户所发表时，显示发帖者特有的功能
        if (user.getId() == CommonUtil.getCurrentUid()) {
            toolbarTextRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onOnGetPictureSuccess(List<String> paths) {
        mObjects.add(paths);
        mAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onGetCatInfoSuccess(Catinfo catinfo) {
//
//        mCatInfo = catinfo;
//        if (catinfo != null) {
//            mObjects.add(catinfo);
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public void onApplySuccess() {
        ToastUtil.showShortToastCenter("已成功发送申请，等待作者审核");
    }


    @Override
    public void onPassSuccess() {
        ToastUtil.showShortToastCenter("审核通过！");
    }

    @Override
    public void onLockSuccess() {
        ToastUtil.showShortToastCenter("锁定成功");
        setResult(ApiConfig.RESULTCODE_NOTIFICATION);
        finish();
    }

    @Override
    public void onUnlockSuccess() {
        ToastUtil.showShortToastCenter("解锁成功");
    }

    @Override
    public void onDeleteSuccess() {
        ToastUtil.showShortToastCenter("删除成功");
        setResult(ApiConfig.RESULTCODE_NOTIFICATION);
        finish();
    }

//    @Override
//    public void onGetApplySuccess(Apply reviewApply) {
//
//    }
//
//    @Override
//    public Apply getApply() {
//        return reviewApply;
//    }

//    @Override
//    public void onChoiceDone() {
//
//    }


    @OnClick({R.id.btn_comment, R.id.btn_like, R.id.btn_share, R.id.editText3, R.id.btn_pass,
            R.id.btn_nopass,R.id.toolbar_text_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment: {
                if (!isAtComment) {
                    isAtComment = true;
                    //记录上次浏览位置
                    index = mListNotedetail.getFirstVisiblePosition();
                    View v = mListNotedetail.getChildAt(0);
                    top = (v == null) ? 0 : v.getTop();
                    mListNotedetail.smoothScrollToPosition(2);
                } else {
                    isAtComment = false;
                    mListNotedetail.setSelectionFromTop(index, top);
                }

            }
            break;
            case R.id.btn_like:
                if (!thumbed) {
                    mPresenter.thumb();
                }
                break;
            case R.id.btn_share:
//                ToastUtil.showShortToastCenter("响应分享操作");
                break;
                //底部评论框
            case R.id.editText3: {
                if (!CommonUtil.isLogined()) {
                    CommonUtil.snackBar(mContext,
                            "您还未登录","登录",new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(LoginActivity.class);
                                }
                            });
                } else {
                    final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                    final BottomCommentDialogFragment bottomCommentDialogFragment = BottomCommentDialogFragment.newInstance();
                    bottomCommentDialogFragment.setReplyListener(new BottomCommentDialogFragment.OnReplyListener() {
                        @Override
                        public void onSucess() {
                            bottomCommentDialogFragment.dismiss();
                            mObjects.clear();//清除旧数据
                            mPresenter.getPictue();//从新获取帖子数据

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
            case R.id.btn_pass: {
                CommonUtil.showDialog(mContext, "提示", "确认审核通过该帖子？", "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //响应审核操作
                        mPresenter.passNote();
                    }
                }, "取消", null);
            }
            break;
            case R.id.btn_nopass: {
                //响应驳回帖子操作
                final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                final BottomCommentDialogFragment bottomCommentDialogFragment = BottomCommentDialogFragment.newInstance();
                bottomCommentDialogFragment.setReplyListener(new BottomCommentDialogFragment.OnReplyListener() {
                    @Override
                    public void onSucess() {
                        ToastUtil.showShortToastCenter("驳回操作成功!");
                        bottomCommentDialogFragment.dismiss();
                    }


                    @Override
                    public void onError(String errorMessage) {
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putInt("nid", getNoteId());
                bundle.putInt("unpass", 1);
                bottomCommentDialogFragment.setArguments(bundle);
                bottomCommentDialogFragment.show(mFragTransaction, "111");
            }
            break;
            case R.id.toolbar_text_right:{

            }break;
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("帖子操作");
        menu.add(0,1,1,"编辑帖子");
        if(verified==3){
            menu.add(0,2,2,"解锁帖子");

        }else{
            menu.add(0,2,2,"锁定帖子");

        }
        menu.add(0,3,3,"删除帖子");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:ToastUtil.showShortToastCenter("响应编辑帖子操作");break;
            case 2:{
                if(verified==3){
                    mPresenter.unlockNote();
                }else{
                    mPresenter.lockNote();
                }
            }break;

            case 3:mPresenter.deleteNote();break;
        }
        return true;
    }
}
