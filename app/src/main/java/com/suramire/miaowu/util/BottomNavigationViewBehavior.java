package com.suramire.miaowu.util;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Suramire on 2017/10/16.
 * 自定义行为 作用于首页
 * 使底部导航跟随Toolbar显示隐藏
 */

public class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<View> {
    public BottomNavigationViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int top = ((AppBarLayout.Behavior) ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior()).getTopAndBottomOffset();
        ViewCompat.setTranslationY(child,-top);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
