package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.NoteContract;
import com.suramire.miaowu.presenter.NotePresenter;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/27.
 */

public class NoteListActivity extends BaseListActivity<NotePresenter> implements NoteContract.View {

    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void cancelLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {
        // TODO: 2018/1/27 这里显示帖子列表
        if(data!=null){
            List<Note> notes = (List<Note>) data;
            if(notes.size()>0){
                showList();
                listview.setLayoutManager(new LinearLayoutManager(mContext));
                listview.setAdapter(new CommonRecyclerAdapter<Note>(mContext,R.layout.item_note, notes) {

                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final Note item, int position) {
                        helper.setText(R.id.note_title,item.getTitle())
                                .setText(R.id.note_content,item.getContent())
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
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍候……");
        toolbarImageLeft.setImageResource(R.drawable.ic_arrow_back_black);
        toolbarTextCenter.setText("帖子列表");
        int uid = getIntent().getIntExtra("uid", 0);
        if(uid!=0){
            // TODO: 2018/1/27 这里根据用户id获取其发表的帖子
            mPresenter.getNotesByUser(uid);
        }else{
            ToastUtil.showShortToastCenter("获取帖子列表失败:未知的用户编号");
        }
    }



    @OnClick(R.id.toolbar_image_left)
    public void onViewClicked() {
        finish();
    }


}
