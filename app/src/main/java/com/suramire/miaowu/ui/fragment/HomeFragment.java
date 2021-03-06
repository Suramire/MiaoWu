package com.suramire.miaowu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.HomeContract;
import com.suramire.miaowu.presenter.HomePresenter;
import com.suramire.miaowu.ui.CatDetailActivity;
import com.suramire.miaowu.ui.NoteDetailActivity;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.PicassoUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;

import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2018/1/25.
 */

public class HomeFragment extends BaseListFragment<HomePresenter> implements HomeContract.View {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    private int position;

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
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

    }

    @Override
    public void createPresenter() {
        mPresenter = new HomePresenter();
    }

    @Override
    public void initView() {
        position = 0;
        showEmpty("没有数据，请下拉刷新");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (position) {
                    case 0:
                        mPresenter.getData();
                        break;
                    case 1:
                        mPresenter.listCats();
                        break;
                }
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                switch (position) {
                    case 0:
                        mPresenter.getData();
                        break;
                    case 1:
                        mPresenter.listCats();
                        break;
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
    public void clearData() {
        listview.setAdapter(null);
    }

    @Override
    public void onGetCatListSuccess(List<Catinfo> catinfos) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        if (catinfos != null && catinfos.size() != 0) {
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<Catinfo>(mContext, R.layout.item_cat, catinfos) {
                @Override
                public void onUpdate(BaseAdapterHelper helper, final Catinfo item, int position) {
                    helper.setText(R.id.tv_id, "编号：" + item.getId())
                            .setText(R.id.tv_adddate, "添加时间：" + dateFormat.format(item.getAdddate()))
                            .setText(R.id.tv_type, "品种：" + (item.getType() == null ? "未知" : item.getType()));
                    helper.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("cid", item.getId());
                            startActivity(CatDetailActivity.class, bundle);
                        }
                    });
                    PicassoUtil.show(ApiConfig.BASECATPICEURL + item.getId() + "_0.png", (ImageView) helper.getView(R.id.catimg));
                }
            });
        } else {
            showEmpty("暂无猫咪信息");
        }
    }

    @Override
    public void onGetNoteListSuccess(List<M> mList) {

        if (mList != null && mList.size() > 0) {
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<M>(mContext, R.layout.item_home, mList) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, M item, int position) {

                    final Note note = (Note) GsonUtil.jsonToObject(item.getStringx(), Note.class);
                    String firstPhoto = item.getStringz();
                    User user = (User) GsonUtil.jsonToObject(item.getStringy(), User.class);
                    PicassoUtil.show(BASNOTEPICEURL + firstPhoto, (ImageView) helper.getView(R.id.noteimg),
                            R.mipmap.ic_loading, R.mipmap.ic_loading_error);
                    PicassoUtil.showIcon(BASUSERPICEURL + user.getIcon(), (ImageView) helper.getView(R.id.anthorimg));
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
                            .setText(R.id.textView7, note.getThumbs() + "")
                            .setText(R.id.notepublishtime, CommonUtil.getHowLongAgo(note.getPublish()))
                            .setText(R.id.textView6, item.getIntx() + "")
                            .setText(R.id.authorname, user.getNickname());
                }
            });
        } else {
            showEmpty("暂无新帖子");
        }
    }

}
