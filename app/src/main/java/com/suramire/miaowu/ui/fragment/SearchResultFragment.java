package com.suramire.miaowu.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.SearchContract;
import com.suramire.miaowu.presenter.HomePresenter;
import com.suramire.miaowu.presenter.SearchPresenter;
import com.suramire.miaowu.ui.NoteDetailActivity;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.FileUtil;
import com.suramire.miaowu.util.L;

import java.util.List;

/**
 * Created by Suramire on 2018/1/26.
 */

public class SearchResultFragment extends BaseListFragment<SearchPresenter> implements SearchContract.View {


    private ProgressDialog progressDialog;
    private String query;

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
        return R.layout.activity_list;
    }

    @Override
    public void createPresenter() {
        mPresenter = new SearchPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        query = arguments.getString("query");
        L.e("query@ " + query);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("请稍候……");
        mPresenter.searchNote();


    }


    @Override
    public void onNoteSuccess(List<Note> notes) {
        if(notes!=null && notes.size()>0){
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<Note>(mContext, R.layout.item_search_note, notes) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, final Note item, int position) {
                    helper.setText(R.id.tv_note_title, item.getTitle())
                            .setText(R.id.textView9, item.getContent())
                            .setText(R.id.tv_note_time, CommonUtil.timeStampToDateString(item.getPublish()))
                            .setText(R.id.tv_author_name, item.getUid()+"")
                            .setOnClickListener(R.id.ll_note, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mContext, NoteDetailActivity.class);
                                    intent.putExtra("noteId", item.getId());
                                    intent.putExtra("userId", item.getUid());
                                    startActivity(intent);
                                }
                            });
                    // TODO: 2018/3/26 这里显示作者的用户名
                }
            });
        }else{
            showEmpty(null);
            swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public String getQuery() {
        return query;
    }
}
