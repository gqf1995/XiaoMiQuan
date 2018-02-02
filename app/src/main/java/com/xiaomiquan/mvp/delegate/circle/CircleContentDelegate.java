package com.xiaomiquan.mvp.delegate.circle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleContentDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_content;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_creator;
        public TextView tv_num;
        public LinearLayout lin_my_circle;
        public IconFontTextview tv_all;
        public TextView tv_rank;
        public RecyclerView pull_recycleview;
        public SwipeRefreshLayout swipeRefreshLayout;
        public FloatingActionButton flt_send;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_creator = (TextView) rootView.findViewById(R.id.tv_creator);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.lin_my_circle = (LinearLayout) rootView.findViewById(R.id.lin_my_circle);
            this.tv_all = (IconFontTextview) rootView.findViewById(R.id.tv_all);
            this.tv_rank = (TextView) rootView.findViewById(R.id.tv_rank);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.flt_send = (FloatingActionButton) rootView.findViewById(R.id.flt_send);
        }

    }
}
