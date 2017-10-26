package com.suramire.miaowu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suramire.miaowu.R;

/**
 * Created by Suramire on 2017/10/25.
 */

public class IntroductionFragment extends Fragment {

    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView==null){
            mContentView = inflater.inflate(R.layout.fragment_profile_introduction, container,false);
        }else{


        }
        return mContentView;

    }
}
