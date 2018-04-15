package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.contract.NoteContract;
import com.suramire.miaowu.presenter.CatPresenter;
import com.suramire.miaowu.presenter.NotePresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/27.
 */

public class ApplyListActivity extends BaseListActivity<CatPresenter> implements CatContract.View {

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


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_list_toolbar;
    }

    @Override
    public void createPresenter() {
        mPresenter = new CatPresenter();
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍候……");
        toolbarImageLeft.setImageResource(R.drawable.ic_arrow_back_black);
        toolbarTextCenter.setText("申请列表");
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.listAppliedCat();
    }

    @OnClick(R.id.toolbar_image_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public Catinfo getCatInfo() {
        return null;
    }

    @Override
    public int getCatId() {
        return 0;
    }

    @Override
    public int getUid() {
        return 0;
    }

    @Override
    public void onAddCatSuccess(int cid) {

    }

    @Override
    public void onApplyCatSuccess() {

    }

    @Override
    public List<String> getStringPaths() {
        return null;
    }

    @Override
    public void onGetAllPicturesSuccess(List<String> paths) {

    }

    @Override
    public void onListAppliedCatSuccess(List<M> catinfo) {
        if(catinfo.size()>0){
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<M>(mContext,R.layout.item_apply,catinfo) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, final M item, int position) {
                    final Catinfo cat = (Catinfo) GsonUtil.jsonToObject(item.getStringy(),Catinfo.class);
                    helper.setText(R.id.textView4,"申请人:"+item.getStringx())
                            .setText(R.id.textView2,"编号："+cat.getId())
                            .setText(R.id.textView3, "申请时间："+CommonUtil.dateToString(cat.getAdoptdate()));
                    helper.setOnClickListener(R.id.ll_apply, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("aid",cat.getId());
                            startActivity(ApplyDetailActivity.class,bundle);
                        }
                    });

                }
            });
        }else{
            showEmpty("暂无待审核领养申请");
        }

    }


//    @Override
//    public void onGetNoteByUserSuccess(List<Note> notes) {
//
//            if(notes.size()>0){
//                showList();
//                listview.setLayoutManager(new LinearLayoutManager(mContext));
//                listview.setAdapter(new CommonRecyclerAdapter<Note>(mContext,R.layout.item_note, notes) {
//
//                    @Override
//                    public void onUpdate(BaseAdapterHelper helper, final Note item, int position) {
//                        helper.setText(R.id.note_title,item.getTitle())
//                                .setText(R.id.note_content,item.getContent())
//                                .setOnClickListener(R.id.ll_note, new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(mContext, NoteDetailActivity.class);
//                                        intent.putExtra("noteId",item.getId());
//                                        intent.putExtra("verifyApply", item.getVerified());
//                                        intent.putExtra("userId",item.getUid());
//                                        startActivity(intent);
//                                    }
//                                });
//                    }
//                });
//            }else{
//                showEmpty("该用户还没发帖子");
//            }
//
//        }

}
