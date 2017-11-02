package com.suramire.miaowu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.suramire.miaowu.R;

/**
 * Created by Suramire on 2017/10/25.
 */

public class PublishFragment extends Fragment {
    private View mContentView;
    private ListView mListView;
    private Context mContext = getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView==null){
            mContentView = inflater.inflate(R.layout.fragment_profile_publish, container,false);
            mListView = mContentView.findViewById(R.id.list_publish);
            View emptyView = mContentView.findViewById(R.id.rl_empty_profile);
            View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_note_more, null);
//            ArrayList<String> list = new ArrayList<>();
//            list.add(BASEURL+"upload/cat.jpg");
//            list.add(BASEURL+"upload/cat1.jpg");
//            list.add(BASEURL+"upload/cat2.jpg");
//            list.add(BASEURL+"upload/cat3.jpg");
//            list.add(BASEURL+"upload/cat4.jpg");
//
//            list.add(BASEURL+"upload/cat.jpg");
//            list.add(BASEURL+"upload/cat1.jpg");
//            list.add(BASEURL+"upload/cat2.jpg");
//            list.add(BASEURL+"upload/cat3.jpg");
//            list.add(BASEURL+"upload/cat4.jpg");
////
////            list.add(BASEURL+"upload/cat.jpg");
////            list.add(BASEURL+"upload/cat1.jpg");
////            list.add(BASEURL+"upload/cat2.jpg");
////            list.add(BASEURL+"upload/cat3.jpg");
////            list.add(BASEURL+"upload/cat4.jpg");

//            mListView.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.item_notelist,list) {
//                @Override
//                public void onUpdate(BaseAdapterHelper helper, String item, int position) {
//
//                }
//            });
            mListView.setEmptyView(emptyView);
            mListView.addFooterView(footerView);

        }else{

        }
        return mContentView;
    }
}
