package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatLinearLayout;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareWebDelegate extends BaseMyPullDelegate {

    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square_web;
    }

    public static class ViewHolder {
        public View rootView;
        public FrameLayout fl_root;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.fl_root = (FrameLayout) rootView.findViewById(R.id.fl_root);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}
