package com.suramire.miaowu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suramire.miaowu.R;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

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
        mClasses =new Class[]{Reply.class, Note.class, ArrayList.class};
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
        }
        return convertView;
    }

    private View getView0(int position, View convertView, ViewGroup parent){
        VH0 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reply, parent, false);
            mVH = new VH0();
            mVH.mtvNickname = convertView.findViewById(R.id.reply_nickname);
            mVH.mtvContent = convertView.findViewById(R.id.reply_content);

            convertView.setTag(mVH);
        }else{
            mVH = (VH0) convertView.getTag();
        }
        mVH.mtvNickname.setText(((Reply) mList.get(position)).getUid() + "");
        mVH.mtvContent.setText(((Reply) mList.get(position)).getReplycontent());
        if(isFirst){
            mVH.mtvTitle = convertView.findViewById(R.id.tv_title_reply);
            mVH.mtvTitle.setVisibility(View.VISIBLE);
            isFirst = false;
        }
        return convertView;
    }

    private View getView1(int position, View convertView, ViewGroup parent){
        VH1 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notedeatil, parent, false);
            mVH = new VH1();
            mVH.mtvTitle = convertView.findViewById(R.id.notedetail_title);
            mVH.mtvContent = convertView.findViewById(R.id.notedetail_content);
            convertView.setTag(mVH);
        }else{
            mVH = (VH1) convertView.getTag();
        }
        mVH.mtvTitle.setText(((Note)mList.get(position)).getTitle());
        mVH.mtvContent.setText(((Note)mList.get(position)).getContent());
        return convertView;
    }



    private View getView2(int position, View convertView, ViewGroup parent){
        VH2 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_banner, null, false);
            mVH = new VH2();
            mVH.mBanner = convertView.findViewById(R.id.banner);
            convertView.setTag(mVH);
        }else{
            mVH = (VH2) convertView.getTag();
        }
        mVH.mBanner.setImageLoader(new GlideImageLoader())
                .setImages((List<String>)getItem(position))
                .isAutoPlay(false)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        return convertView;
    }


    class VH0 {
        TextView mtvNickname,mtvContent;
        TextView mtvTitle;
    }

    class VH1 {
        TextView mtvTitle,mtvContent;
    }

    class VH2 {
        Banner mBanner;
    }


}
