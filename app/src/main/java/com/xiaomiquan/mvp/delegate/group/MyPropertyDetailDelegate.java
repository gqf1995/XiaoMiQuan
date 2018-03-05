package com.xiaomiquan.mvp.delegate.group;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.util.ArrayList;
import java.util.List;

public class MyPropertyDetailDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.scrollView_scroll.setTabAndPager(viewHolder.lin_tab,
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px),
                viewHolder.pull_recycleview, false);
        initProperty();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_property_detail;

    }

    public void initProperty() {
        PieChart pieChart = viewHolder.pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");  //设置描述信息
        pieChart.setExtraOffsets(5, 10, 5, 5);  //设置间距
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText("");  //设置饼状图中间文字
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setTouchEnabled(false);  //设置是否响应点击触摸
        pieChart.setDrawCenterText(false);  //设置是否绘制中心区域文字
        pieChart.setRotationEnabled(false); //设置是否旋转
        pieChart.setHighlightPerTapEnabled(false);  //设置是否高亮显示触摸的区域
        pieChart.setDrawHoleEnabled(false); //设置是否为空心
        pieChart.setData(getDefaultPieData());  //设置数据
//        pieChart.setOnChartValueSelectedListener(this); //设置选中监听
//        pieChart.setDrawMarkerViews(false);  //设置是否绘制标记
    }

    private PieData getDefaultPieData() {

        ArrayList entries = new ArrayList<>();
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
        ArrayList colors = new ArrayList<>();  //控制不同绘制区域的颜色
        dataSet.setColors(colors);
        PieData data = new PieData();
        data.setDataSet(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
        return data;
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