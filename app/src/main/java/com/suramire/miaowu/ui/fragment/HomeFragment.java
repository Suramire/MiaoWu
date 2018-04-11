package com.suramire.miaowu.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseFragment;
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

import butterknife.Bind;

import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2018/1/25.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int currentPosition;

    @Override
    public int bindLayout() {
        return R.layout.activity_list2;
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
        final List<Multi> multis = (List<Multi>) data;
        if (multis.size() > 0) {

            listview.setAdapter(new CommonAdapter<Multi>(mContext, R.layout.item_home, multis) {

                @Override
                public void onUpdate(final BaseAdapterHelper helper, final Multi item, int position) {
                    final Note note = item.getmNote();
                    NotePhoto notePhoto = item.getmNotePhoto();
                    User user = item.getmUser();
                    PicassoUtil.show(BASNOTEPICEURL + notePhoto.getName(), (ImageView) helper.getView(R.id.noteimg),
                            R.mipmap.ic_loading, R.mipmap.ic_loading_error);
                    PicassoUtil.show(BASUSERPICEURL + user.getIcon(), (ImageView) helper.getView(R.id.anthorimg));
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
                            .setText(R.id.textView7,note.getThumbs()+"")
                            .setText(R.id.notepublishtime, CommonUtil.getHowLongAgo(note.getPublish()))
                            .setText(R.id.textView6, item.getReplyNumber() + "")
                            .setText(R.id.authorname, user.getNickname());
                }
            });
//            Toast.makeText(mContext, "成功获取" + multis.size() + "条数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "暂无新帖子", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void createPresenter() {
        mPresenter = new HomePresenter();
    }

    @Override
    public void initView() {
        tvEmpty.setText("没有帖子数据，下拉刷新");
        listview.setEmptyView(llEmpty);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(tablayout.getSelectedTabPosition()+1);
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPresenter.getData(tab.getPosition() +1);

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



}
