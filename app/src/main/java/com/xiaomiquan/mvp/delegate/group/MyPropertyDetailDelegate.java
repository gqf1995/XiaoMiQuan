package com.xiaomiquan.mvp.delegate.group;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.MyAsset;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import skin.support.widget.SkinCompatLinearLayout;

public class MyPropertyDetailDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.scrollView_scroll.setTabAndPager(viewHolder.lin_tab,
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_80px),
                viewHolder.fl_pull
                , false);
    }

    public void initMyAsset(MyAsset myAsset) {
        if (myAsset != null) {
            viewHolder.tv_usd_num.setText(BigUIUtil.getinstance().bigPrice(myAsset.getTotalAmount()));
            viewHolder.tv_usable_usd.setText(BigUIUtil.getinstance().bigPrice(myAsset.getBalance()));

            //初始化 饼状图
            PieChart mPieChart = viewHolder.pieChart;
            // 显示百分比
            mPieChart.setUsePercentValues(true);
            // 描述信息
            mPieChart.setDescription("");

        /*
            设置饼图中心是否是空心的
            true 中间是空心的，环形图
            false 中间是实心的 饼图
         */
            mPieChart.setDrawHoleEnabled(false);
            // enable rotation of the chart by touch
            mPieChart.setRotationEnabled(false);
            mPieChart.setHighlightPerTapEnabled(false);
            Legend mLegend = mPieChart.getLegend();
            mLegend.setEnabled(false);
            // add a selection listener
            // mPieChart.setOnChartValueSelectedListener(this);
            TreeMap<String, Float> data = new TreeMap<>();
            float other = 0f;
            for (int i = 0; i < myAsset.getCoins().size(); i++) {
                if (myAsset.getCoins().get(i).getRatio() < 0.08) {
                    other = other + myAsset.getCoins().get(i).getRatio();
                    data.put(CommonUtils.getString(R.string.str_other), other);
                } else {
                    data.put(myAsset.getCoins().get(i).getSymbol(), myAsset.getCoins().get(i).getRatio());
                }
            }
            setData(data, mPieChart);
        }
    }

    public void setData(TreeMap<String, Float> data, PieChart mPieChart) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        int i = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            float value = (float) entry.getValue();
            xVals.add(key);
            yVals1.add(new Entry(value, i++));
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        // dataSet.setSelectionShift(0f);

        PieData data1 = new PieData(xVals, dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(10f);
        data1.setValueTextColor(Color.BLACK);
        mPieChart.setData(data1);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_property_detail;

    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_usd_num;
        public TextView tv_usable_usd;
        public PieChart pieChart;
        public TextView tv_type;
        public LinearLayout lin_tab;
        public RecyclerView pull_recycleview;
        public FrameLayout fl_rcv;
        public SkinCompatLinearLayout fl_pull;
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
            this.fl_rcv = (FrameLayout) rootView.findViewById(R.id.fl_rcv);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
            this.scrollView_scroll = (JudgeNestedScrollView) rootView.findViewById(R.id.scrollView_scroll);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}