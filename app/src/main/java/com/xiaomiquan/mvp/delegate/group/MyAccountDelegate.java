package com.xiaomiquan.mvp.delegate.group;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.MyAsset;
import com.xiaomiquan.entity.bean.group.EarningsMovements;
import com.xiaomiquan.entity.bean.group.TeamInfo;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.widget.chart.MyLeftRateMarkerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyAccountDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.linechart.setNoDataText(CommonUtils.getString(R.string.str_chart_nodata));
    }


    public void initMyAsset(MyAsset myAsset) {
        if (myAsset != null) {
            viewHolder.tv_total_assets.setText(BigUIUtil.getinstance().bigPrice(myAsset.getTotalAmount()));
            viewHolder.tv_usable.setText(BigUIUtil.getinstance().bigPrice(myAsset.getBalance()));

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

    public void initRate(String data) {
        String monthRate1 = GsonUtil.getInstance().getValue(data, "monthRate");
        String weekRate1 = GsonUtil.getInstance().getValue(data, "weekRate");
        String yesterdayRate1 = GsonUtil.getInstance().getValue(data, "yesterdayRate");
        String totalRate1 = GsonUtil.getInstance().getValue(data, "totalRate");
        double monthRate, totalRate, weekRate, yesterdayRate;
        if (!TextUtils.isEmpty(monthRate1)) {
            monthRate = Double.parseDouble(monthRate1);
        } else {
            monthRate = 0;
        }
        if (!TextUtils.isEmpty(totalRate1)) {
            totalRate = Double.parseDouble(totalRate1);
        } else {
            totalRate = 0;
        }
        if (!TextUtils.isEmpty(weekRate1)) {
            weekRate = Double.parseDouble(weekRate1);
        } else {
            weekRate = 0;
        }
        if (!TextUtils.isEmpty(yesterdayRate1)) {
            yesterdayRate = Double.parseDouble(yesterdayRate1);
        } else {
            yesterdayRate = 0;
        }
        BigUIUtil.getinstance().rateTextView(monthRate, viewHolder.tv_month_earnings);
        BigUIUtil.getinstance().rateTextView(totalRate, viewHolder.tv_cumulative_earnings);
        BigUIUtil.getinstance().rateTextView(weekRate, viewHolder.tv_week_earnings);
        BigUIUtil.getinstance().rateTextView(yesterdayRate, viewHolder.tv_yesterday_earnings);

    }

    protected XAxis xAxisKline;
    //Y轴左侧的线
    protected YAxis axisLeftKline;
    //Y轴右侧的线
    protected YAxis axisRightKline;

    public void initEarningsMovements(EarningsMovements earningsMovements) {
        String startTime;
        String endTime;
        startTime = TimeUtils.millis2String(earningsMovements.getStartTime(), com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT);
        endTime = TimeUtils.millis2String(earningsMovements.getEndTime(), com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT);
        viewHolder.tv_chart_time.setText(startTime + "-" + endTime);
        viewHolder.tv_start_time.setText(startTime);
        viewHolder.tv_end_time.setText(endTime);
        LineChart mChartKline = viewHolder.linechart;
        mChartKline.setScaleEnabled(false);//启用图表缩放事件
        mChartKline.setDrawBorders(false);//是否绘制边线
        mChartKline.setBorderWidth(1);//边线宽度，单位dp
        mChartKline.setDragEnabled(false);//启用图表拖拽事件
        mChartKline.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartKline.setBorderColor(CommonUtils.getColor(R.color.border_color));//边线颜色
        mChartKline.setDescription("");//右下角对图表的描述信息
        mChartKline.setMinOffset(0f);
        mChartKline.setExtraOffsets(20f, 10f, 30f, 0f);

        Legend lineChartLegend = mChartKline.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        xAxisKline = mChartKline.getXAxis();
        xAxisKline.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisKline.setTextColor(CommonUtils.getColor(R.color.transparent));//设置字的颜色
        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(false);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mChartKline.getAxisLeft();
        axisLeftKline.setDrawGridLines(false);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(CommonUtils.getColor(R.color.color_font4));
        axisLeftKline.setTextSize(10);
        //        axisLeftKline.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布


        axisRightKline = mChartKline.getAxisRight();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布


        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (int i = 0; i < earningsMovements.getRates().size(); i++) {
            lineEntries.add(new Entry(earningsMovements.getRates().get(i), i));
            time.add("");
        }
        LineDataSet lineDataSetMA7 = new LineDataSet(lineEntries, "ma");
        lineDataSetMA7.setDrawValues(false);
        lineDataSetMA7.setDrawCircles(false);
        lineDataSetMA7.setDrawCircleHole(false);
        lineDataSetMA7.setColor(CommonUtils.getColor(R.color.color_blue));
        LineData lineData = new LineData(time, lineDataSetMA7);
        MyLeftRateMarkerView leftMarkerView = new MyLeftRateMarkerView(viewHolder.rootView.getContext(), R.layout.mymarkerview);
        mChartKline.setMarkerView(leftMarkerView);
        mChartKline.setData(lineData);
        //setHandler(mChartKline);

        mChartKline.moveViewToX(lineEntries.size() - 1);
        mChartKline.setMaxVisibleValueCount(earningsMovements.getRates().size());
        mChartKline.invalidate();

    }

    public void initTeaminfo(TeamInfo teamInfo) {
        double monthRate, totalRate, weekRate, yesterdayRate;
        if (!TextUtils.isEmpty(teamInfo.getMonthProfit())) {
            monthRate = Double.parseDouble(teamInfo.getMonthProfit());
        } else {
            monthRate = 0;
        }
        if (!TextUtils.isEmpty(teamInfo.getTotalProfit())) {
            totalRate = Double.parseDouble(teamInfo.getTotalProfit());
        } else {
            totalRate = 0;
        }
        if (!TextUtils.isEmpty(teamInfo.getWeekProfit())) {
            weekRate = Double.parseDouble(teamInfo.getWeekProfit());
        } else {
            weekRate = 0;
        }
        if (!TextUtils.isEmpty(teamInfo.getYesterdayProfit())) {
            yesterdayRate = Double.parseDouble(teamInfo.getYesterdayProfit());
        } else {
            yesterdayRate = 0;
        }
        BigUIUtil.getinstance().rateTextView(monthRate, viewHolder.tv_month_earnings);
        BigUIUtil.getinstance().rateTextView(totalRate, viewHolder.tv_cumulative_earnings);
        BigUIUtil.getinstance().rateTextView(weekRate, viewHolder.tv_week_earnings);
        BigUIUtil.getinstance().rateTextView(yesterdayRate, viewHolder.tv_yesterday_earnings);


        EarningsMovements earningsMovements = new EarningsMovements();
        earningsMovements.setEndTime(teamInfo.getEndTime());
        earningsMovements.setStartTime(teamInfo.getStartTime());
        earningsMovements.setRates(teamInfo.getRates());
        initEarningsMovements(earningsMovements);
        BigUIUtil.getinstance().rateTextView(Double.parseDouble(teamInfo.getCurrProfit()), viewHolder.tv_today_earnings);
        viewHolder.tv_daily_operation.setText(teamInfo.getCount() + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_account;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public LinearLayout lin_gameplay_introduced;
        public LinearLayout lin_revenue_ranking;
        public TextView tv_total_assets;
        public TextView tv_usable;
        public PieChart pieChart;
        public LinearLayout lin_assets_report;
        public TextView tv_today_earnings;
        public TextView tv_daily_operation;
        public TextView tv_cumulative_earnings;
        public TextView tv_yesterday_earnings;
        public TextView tv_week_earnings;
        public TextView tv_month_earnings;
        public TextView tv_chart_time;
        public LineChart linechart;
        public TextView tv_start_time;
        public TextView tv_end_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.lin_gameplay_introduced = (LinearLayout) rootView.findViewById(R.id.lin_gameplay_introduced);
            this.lin_revenue_ranking = (LinearLayout) rootView.findViewById(R.id.lin_revenue_ranking);
            this.tv_total_assets = (TextView) rootView.findViewById(R.id.tv_total_assets);
            this.tv_usable = (TextView) rootView.findViewById(R.id.tv_usable);
            this.pieChart = (PieChart) rootView.findViewById(R.id.pieChart);
            this.lin_assets_report = (LinearLayout) rootView.findViewById(R.id.lin_assets_report);
            this.tv_today_earnings = (TextView) rootView.findViewById(R.id.tv_today_earnings);
            this.tv_daily_operation = (TextView) rootView.findViewById(R.id.tv_daily_operation);
            this.tv_cumulative_earnings = (TextView) rootView.findViewById(R.id.tv_cumulative_earnings);
            this.tv_yesterday_earnings = (TextView) rootView.findViewById(R.id.tv_yesterday_earnings);
            this.tv_week_earnings = (TextView) rootView.findViewById(R.id.tv_week_earnings);
            this.tv_month_earnings = (TextView) rootView.findViewById(R.id.tv_month_earnings);
            this.tv_chart_time = (TextView) rootView.findViewById(R.id.tv_chart_time);
            this.linechart = (LineChart) rootView.findViewById(R.id.linechart);
            this.tv_start_time = (TextView) rootView.findViewById(R.id.tv_start_time);
            this.tv_end_time = (TextView) rootView.findViewById(R.id.tv_end_time);
        }

    }
}