package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import skin.support.widget.SkinCompatLinearLayout;

public class LiveDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acitvity_live;
    }


    public static class ViewHolder {
        public View rootView;
        public RecyclerView pull_recycleview;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}