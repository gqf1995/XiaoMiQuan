package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.github.mikephil.charting.charts.PieChart;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.JudgeNestedScrollView;

public class MyPropertyDetailDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_property_detail;
    }

    public void initProperty() {
        PieChart pieChart=viewHolder.pieChart;

    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_usd_num;
        public TextView tv_usable_usd;
        public PieChart pieChart;
        public TextView tv_type;
        public LinearLayout lin_tab;
        public RecyclerView pull_recycleview;
        public JudgeNestedScrollView scrollView_scroll;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_usd_num = (TextView) rootView.findViewById(R.id.tv_usd_num);
            this.tv_usable_usd = (TextView) rootView.findViewById(R.id.tv_usable_usd);
            this.pieChart = (PieChart) rootView.findViewById(R.id.pieChart);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.scrollView_scroll = (JudgeNestedScrollView) rootView.findViewById(R.id.scrollView_scroll);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}