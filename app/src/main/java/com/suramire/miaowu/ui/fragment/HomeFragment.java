package com.suramire.miaowu.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.NotePhoto;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.presenter.HomePresenter;
import com.suramire.miaowu.ui.NoteDetailActivity;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.PicassoUtil;

import java.util.List;

import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2018/1/25.
 */

public class HomeFragment extends BaseListFragment<HomePresenter> implements HomeContract.View {

    @Override
    public int bindLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void cancelLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccess(Object data) {
        showList();
        final List<Multi> notes = (List<Multi>) data;
        if (notes.size() > 0) {

            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<Multi>(mContext, R.layout.item_home, notes) {

                @Override
                public void onUpdate(final BaseAdapterHelper helper, final Multi item, int position) {
                    final Note note = item.getmNote();
                    NotePhoto notePhoto = item.getmNotePhoto();
                    User user = item.getmUser();
                    PicassoUtil.show(BASNOTEPICEURL + notePhoto.getName(),(ImageView) helper.getView(R.id.noteimg),
                            R.drawable.ic_loading,R.drawable.ic_loading_error);
                    PicassoUtil.show(BASUSERPICEURL + user.getIcon(),(ImageView) helper.getView(R.id.anthorimg));
                    helper.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, NoteDetailActivity.class);
                            intent.putExtra("noteId", note.getId());
                            intent.putExtra("userId", note.getUid());
                            startActivity(intent);
                        }
                    });
                    helper.setText(R.id.notetitle, note.getTitle())
                            .setText(R.id.notecontent, note.getContent())
                            .setText(R.id.notepublishtime, CommonUtil.getHowLongAgo(note.getPublish()))
                            .setText(R.id.authorname, user.getNickname());
                }
            });
            Toast.makeText(mContext, "成功获取" + notes.size() + "条数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "暂无新帖子", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void createPresenter() {
        mPresenter =new HomePresenter();
    }

    @Override
    public void initView() {
        showEmpty(null);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData();
            }
        });

    }

    @Override
    public void clearData() {
        listview.setAdapter(null);
    }



}
