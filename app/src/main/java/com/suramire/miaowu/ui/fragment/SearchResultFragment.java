package com.suramire.miaowu.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.SearchContract;
import com.suramire.miaowu.presenter.SearchPresenter;
import com.suramire.miaowu.ui.NoteDetailActivity;
import com.suramire.miaowu.ui.UserActivity;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;

import java.util.List;

/**
 * Created by Suramire on 2018/1/26.
 */

public class SearchResultFragment extends BaseListFragment implements SearchContract.View {


    private ProgressDialog progressDialog;
    private String query;
    private int index;
    private List<Note> notes;
    private List<User> users;

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
        notes = (List<Note>) arguments.getSerializable("notes");
        users = (List<User>) arguments.getSerializable("users");
        index = arguments.getInt("index");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("请稍候……");
        //通过下标区别所搜的是帖子还是用户信息
        if(index==1){
            showList(users);
        }else{
            showList(notes);
        }
    }




    private <E> void  showList(final List<E> lists) {
        if(lists!=null && lists.size()>0){
            L.e("list is not null");
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            Class<?> aClass = lists.get(0).getClass();
            if(aClass ==User.class){
                listview.setAdapter(new CommonRecyclerAdapter<User>(mContext, R.layout.item_fan, (List<User>) lists) {

                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final User item, int position) {

                        //显示用户姓名 点击进入用户个人主页
                        helper.setText(R.id.tv_username,item.getNickname())
                                .setOnClickListener(R.id.ll_fans, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(mContext, UserActivity.class);
                                        intent.putExtra("uid",item.getId());
                                        startActivity(intent);
                                    }
                                });
//                    //显示用户图片
                        PicassoUtil.show(item.getIcon(), (ImageView) helper.getView(R.id.img_icon));
                    }
                });
            }else if(aClass == Note.class){
                listview.setAdapter(new CommonRecyclerAdapter<Note>(mContext, R.layout.item_search_note, (List<Note>) lists) {

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
                    }
                });
            }

        }else{
            L.e("list is null");
            showEmpty(null);
            swipeRefreshLayout.setEnabled(false);
        }
    }


    @Override
    public void onUserSuccess(List<User> users) {

    }

    @Override
    public void onNoteSuccess(List<Note> notes) {

    }

    @Override
    public String getQuery() {
        return null;
    }
}
