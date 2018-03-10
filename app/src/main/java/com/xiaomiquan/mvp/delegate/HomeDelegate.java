package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class HomeDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }
    @Override
    protected int getLayoutId() {


        return R.layout.fragment_home;
    }


    public static class ViewHolder {
        public View rootView;
        public FrameLayout fl_web;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.fl_web = (FrameLayout) rootView.findViewById(R.id.fl_web);
        }

    }
}