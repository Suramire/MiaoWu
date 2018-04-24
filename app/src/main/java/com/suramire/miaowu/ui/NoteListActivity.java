package com.suramire.miaowu.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.NoteContract;
import com.suramire.miaowu.presenter.NotePresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

/**
 * Created by Suramire on 2018/1/27.
 */

public class NoteListActivity extends BaseListActivity<NotePresenter> implements NoteContract.View {

    private int uid;


    @Override
    public void onSuccess(Object data) {
        if(data!=null){
            List<Note> notes = (List<Note>) data;
            if(notes.size()>0){
                showList();
                listview.setLayoutManager(new LinearLayoutManager(mContext));
                listview.setAdapter(new CommonRecyclerAdapter<Note>(mContext,R.layout.item_note, notes) {

                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final Note item, int position) {
                        helper.setText(R.id.note_title, item.getTitle())
                                .setText(R.id.note_content, item.getContent())
                                .setText(R.id.note_date, CommonUtil.timeStampToDateString(item.getPublish()))
                                .setOnClickListener(R.id.ll_note, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, NoteDetailActivity.class);
                                        intent.putExtra("noteId", item.getId());
                                        intent.putExtra("userId", item.getUid());
                                        startActivity(intent);
                                    }
                                });
                    }
                });
            }else{
                showEmpty("暂时没有待审核的帖子");
            }
        }
        else{
            showEmpty("暂时没有待审核的帖子");
        }


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_list_toolbar;
    }

    @Override
    public void createPresenter() {
        mPresenter = new NotePresenter();
    }

    @Override
    public void initView() {
        swipeRefreshLayout.setEnabled(false);
        progressDialog.setMessage("正在读取帖子列表，请稍候……");
        setLeftImage(R.drawable.ic_arrow_back_black);
        setTitle("帖子列表");
        uid = getIntent().getIntExtra("uid", 0);//所查看用户的编号
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(uid==-1){
            //管理员显示待审核帖子
            setTitle("待审核帖子列表");
            mPresenter.getUnverifyNotes();

        }else if(uid == CommonUtil.getCurrentUid() && uid!=0){
            //当前登录的用户查看自己的发帖记录
            mPresenter.getAllNotesByUser(uid);
        }else if(uid!=0){
            //其他用户查看帖子记录
            mPresenter.getNotesByUser(uid);
        }else{
            ToastUtil.showLongToastCenter("获取帖子列表失败:未知的用户编号");
        }
    }


    @Override
    public void onGetNoteByUserSuccess(List<Note> notes) {

            if(notes.size()>0){
                showList();
                listview.setLayoutManager(new LinearLayoutManager(mContext));
                listview.setAdapter(new CommonRecyclerAdapter<Note>(mContext,R.layout.item_note, notes) {

                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final Note item, int position) {
                        helper.setText(R.id.note_title,item.getTitle())
                                .setText(R.id.note_content,item.getContent())
                                .setText(R.id.note_date, CommonUtil.timeStampToDateString(item.getPublish()))
                                .setOnClickListener(R.id.ll_note, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, NoteDetailActivity.class);
                                        intent.putExtra("noteId",item.getId());
                                        intent.putExtra("userId",item.getUid());
                                        startActivity(intent);
                                    }
                                });
                    }
                });
            }else{
                showEmpty("该用户还没发帖子");
            }

        }

}
