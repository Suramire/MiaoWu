package com.suramire.miaowu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.ui.ReplyDetailActivity;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import static com.suramire.miaowu.util.Constant.BASNOTEPICEURL;
import static com.suramire.miaowu.util.Constant.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/11/7.
 */

public class MultiItemAdapter extends BaseAdapter {

    private final Class[] mClasses;
    private List  mList;
    private Context mContext;
    private boolean isFirst = true;


    public MultiItemAdapter(Context context , List list) {
        mList = list;
        mContext = context;
        mClasses =new Class[]{Multi.class, Note.class, ArrayList.class, User.class};
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        for(int i=0,size=mClasses.length;i<size;i++){
            if(item.getClass() == mClasses[i]){
                return i;
            }
        }
        return  0;
    }

    @Override
    public int getViewTypeCount() {
        return mClasses.length;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case 0:{
                convertView = getView0(position, convertView, parent);
            }break;
            case 1:{
                convertView = getView1(position, convertView, parent);
            }break;
            case 2:{
                convertView = getView2(position, convertView, parent);
            }break;
            case 3:{
                convertView = getView3(position, convertView, parent);
            }break;
        }
        return convertView;
    }

    private View getView0(int position, View convertView, ViewGroup parent){
        VH0 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reply, parent, false);
            mVH = new VH0();
            mVH.mtvNickname = convertView.findViewById(R.id.reply_user_nickname);
            mVH.mtvContent = convertView.findViewById(R.id.reply_content);
            mVH.mtvReplytime = convertView.findViewById(R.id.reply_date);
            mVH.mimgUserIcon = convertView.findViewById(R.id.reply_user_icon);
            mVH.mtvCount = convertView.findViewById(R.id.reply_count);
            mVH.mll_repleydetail = convertView.findViewById(R.id.ll_replydetail);
            convertView.setTag(mVH);
        }else{
            mVH = (VH0) convertView.getTag();
        }
        final Multi multi = (Multi) mList.get(position);
        Reply reply = multi.getmReply();
        User user = multi.getmUser();
        mVH.mtvNickname.setText(user.getNickname());
        String icon = user.getIcon();
        if(icon!=null)
        Picasso.with(mContext)
                .load(BASUSERPICEURL+icon)
                .into(mVH.mimgUserIcon);
        mVH.mtvCount.setText(multi.getCount()+"");

        mVH.mtvContent.setText(reply.getReplycontent());
        mVH.mtvReplytime.setText(CommonUtil.timeStampToDateString(reply.getReplytime()));
        //第一项添加标头
        if(isFirst){
            mVH.mtvTitle = convertView.findViewById(R.id.tv_title_reply);
            mVH.mtvTitle.setVisibility(View.VISIBLE);
            isFirst = false;
        }
        mVH.mll_repleydetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReplyDetailActivity.class);
                intent.putExtra("multi", multi);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private View getView1(int position, View convertView, ViewGroup parent){
        VH1 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_content, parent, false);
            mVH = new VH1();
            mVH.mtvTitle = convertView.findViewById(R.id.notedetail_title);
            mVH.mtvContent = convertView.findViewById(R.id.notedetail_content);
            mVH.mtvPublishTime = convertView.findViewById(R.id.nt_publishtime);
            mVH.mtvViewcount = convertView.findViewById(R.id.nt_viewcount);
            convertView.setTag(mVH);
        }else{
            mVH = (VH1) convertView.getTag();
        }
        Note note = (Note) mList.get(position);
        mVH.mtvTitle.setText(note.getTitle());
        mVH.mtvContent.setText(note.getContent());
        mVH.mtvPublishTime.setText("发布时间："+CommonUtil.timeStampToDateString(note.getPublish()));
        mVH.mtvViewcount.setText("浏览次数:"+note.getViewcount()+"");
        return convertView;
    }



    private View getView2(int position, View convertView, ViewGroup parent){
        VH2 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_banner, null, false);
            mVH = new VH2();
            mVH.mBanner = convertView.findViewById(R.id.banner);
            convertView.setTag(mVH);
        }else{
            mVH = (VH2) convertView.getTag();
        }
        List<String> items = (List<String>) getItem(position);
        List<String> newItems = new ArrayList<>();
        for (String s:
             items) {
            newItems.add(BASNOTEPICEURL+s);
        }
        mVH.mBanner.setImageLoader(new GlideImageLoader())
                .setImages(newItems)
                .isAutoPlay(false)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        return convertView;
    }

    private View getView3(int position, View convertView, ViewGroup parent){
        VH3 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_profile, null, false);
            mVH = new VH3();
            mVH.mButtonFollow = convertView.findViewById(R.id.btn_nt_follow);
            mVH.mImgUser = convertView.findViewById(R.id.img_nt_profile);
            mVH.mTvNickname = convertView.findViewById(R.id.tv_nt_nickname);
            convertView.setTag(mVH);
        }else{
            mVH = (VH3) convertView.getTag();
        }
        User user = (User) mList.get(position);
        mVH.mTvNickname.setText(user.getNickname());
        mVH.mButtonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "这里响应关注作者事件", Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.with(mContext)
                .load(BASUSERPICEURL+user.getIcon())
                .into(mVH.mImgUser);
        return convertView;
    }


    class VH0 {
        TextView mtvNickname,mtvContent,mtvCount;
        TextView mtvTitle,mtvReplytime;
        ImageView mimgUserIcon;
        LinearLayout mll_repleydetail;
    }

    class VH1 {
        TextView mtvTitle,mtvContent,
            mtvPublishTime,mtvViewcount;
    }

    class VH2 {
        Banner mBanner;
    }

    class VH3{
        ImageView mImgUser;
        TextView mTvNickname;
        Button mButtonFollow;
    }


}
