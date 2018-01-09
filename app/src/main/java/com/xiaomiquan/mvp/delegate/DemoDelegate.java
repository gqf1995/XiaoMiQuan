package com.xiaomiquan.mvp.delegate;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.xiaomiquan.R;

public class DemoDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }


    public static class ViewHolder {
        public View rootView;
        public CombinedChart combinedchart;
        public BarChart barChart;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.combinedchart = (CombinedChart) rootView.findViewById(R.id.combinedchart);
            this.barChart = (BarChart) rootView.findViewById(R.id.barchart);
        }

    }
}