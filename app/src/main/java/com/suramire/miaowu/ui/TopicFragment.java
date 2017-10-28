package com.suramire.miaowu.ui;

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

public class TopicFragment extends Fragment {
    private View mContentView;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView==null){
            mContentView = inflater.inflate(R.layout.fragment_profile_publish, container,false);
            mListView = mContentView.findViewById(R.id.list_publish);

        }else{


        }
        return mContentView;
    }
}
