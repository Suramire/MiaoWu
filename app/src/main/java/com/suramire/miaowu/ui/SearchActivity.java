package com.suramire.miaowu.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.SearchContract;
import com.suramire.miaowu.presenter.SearchPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Suramire on 2018/1/26.
 */

public class SearchActivity extends BaseListActivity<SearchPresenter> implements SearchContract.View {
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
//    @Bind(R.id.viewpager)
//    ViewPager viewpager;
    private String mQuery;
    private List<Note> mNotes;
    private String query;
    private int currentPositiion;

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void createPresenter() {
        mPresenter = new SearchPresenter();
    }

    @Override
    public void initView() {
        currentPositiion = 0;
        swipeRefreshLayout.setEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView.setIconified(false);
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                setupResult(query);
                mQuery = query;
                switch (currentPositiion){
                    case 0:mPresenter.searchNote();break;
                    case 1:mPresenter.searchUser();break;
                }
                L.e("onQueryTextSubmit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPositiion = tab.getPosition();
                switch (currentPositiion){
                    case 0:mPresenter.searchNote();break;
                    case 1:mPresenter.searchUser();break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onUserSuccess(List<User> users) {
        if(users!=null && users.size()>0){
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<User>(mContext, R.layout.item_fan, users) {
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


        }else{
            showEmpty("未搜索到符合条件的用户信息");
        }
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
                }
            });

        }else{
            showEmpty("未搜索到符合条件的帖子信息");
        }
    }

    @Override
    public String getQuery() {
        return mQuery;
    }
}
