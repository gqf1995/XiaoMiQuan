package com.xiaomiquan.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.SlidingTabLayout;
import com.xiaomiquan.R;

public class CircleDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public SlidingTabLayout tl_2;
        public ViewPager viewpager;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_2 = (SlidingTabLayout) rootView.findViewById(R.id.tl_2);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        }

    }
}