package com.xiaomiquan.mvp.delegate.circle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class NewsDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_time;
        public RecyclerView pull_recycleview;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
        }

    }
}