package com.xiaomiquan.mvp.activity;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.mpchart.ConstantTest;
import com.xiaomiquan.mpchart.CoupleChartGestureListener;
import com.xiaomiquan.mpchart.VolFormatter;
import com.xiaomiquan.mvp.databinder.DemoBinder;
import com.xiaomiquan.mvp.delegate.DemoDelegate;
import com.xiaomiquan.widget.chart.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends BaseDataBindActivity<DemoDelegate, DemoBinder> {
    private DataParse mData;
    private ArrayList<KLineBean> kLineDatas;
    XAxis xAxisBar, xAxisK;
    YAxis axisLeftBar, axisLeftK;
    YAxis axisRightBar, axisRightK;
    BarDataSet barDataSet;
    float sum = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewDelegate.viewHolder.barChart.setAutoScaleMinMaxEnabled(true);
            viewDelegate.viewHolder.combinedchart.setAutoScaleMinMaxEnabled(true);

            viewDelegate.viewHolder.combinedchart.notifyDataSetChanged();
            viewDelegate.viewHolder.barChart.notifyDataSetChanged();

            viewDelegate.viewHolder.combinedchart.invalidate();
            viewDelegate.viewHolder.barChart.invalidate();

        }
    };


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("dasdas"));
        initChart();
        getOffLineData();
    }


    private void getOffLineData() {
           /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);

        setData(mData);


    }

    private void initChart() {
        viewDelegate.viewHolder.barChart.setDrawBorders(true);//是否绘制边线
        viewDelegate.viewHolder.barChart.setBorderWidth(1);//边线宽度，单位dp
        viewDelegate.viewHolder.barChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));//边线颜色
        viewDelegate.viewHolder.barChart.setDescription("");//右下角对图表的描述信息
        viewDelegate.viewHolder.barChart.setDragEnabled(true);//启用图表拖拽事件
        viewDelegate.viewHolder.barChart.setScaleYEnabled(false);//启用Y轴上的缩放

        Legend barChartLegend = viewDelegate.viewHolder.barChart.getLegend();
        barChartLegend.setEnabled(false);//是否绘制 Legend 图例

        //BarYAxisFormatter  barYAxisFormatter=new BarYAxisFormatter();
        //bar x y轴
        xAxisBar = viewDelegate.viewHolder.barChart.getXAxis();//控制X轴的
        xAxisBar.setDrawLabels(true);//是否显示X坐标轴上的刻度，默认是true
        xAxisBar.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisBar.setDrawAxisLine(false);//是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisBar.setGridColor(getResources().getColor(R.color.minute_grayLine));

        axisLeftBar = viewDelegate.viewHolder.barChart.getAxisLeft();
        axisLeftBar.setAxisMinValue(0);//设置Y轴坐标最小为多少
        axisLeftBar.setDrawGridLines(false);
        axisLeftBar.setDrawAxisLine(false);
        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftBar.setDrawLabels(true);
        axisLeftBar.setSpaceTop(0);
        axisLeftBar.setShowOnlyMinMax(true);//参数如果为true Y轴坐标只显示最大值和最小值
        axisRightBar = viewDelegate.viewHolder.barChart.getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);
        axisRightBar.setDrawAxisLine(false);
        /****************************************************************/
        viewDelegate.viewHolder.combinedchart.setDrawBorders(true);//是否绘制边线
        viewDelegate.viewHolder.combinedchart.setBorderWidth(1);//边线宽度，单位dp
        viewDelegate.viewHolder.combinedchart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        viewDelegate.viewHolder.combinedchart.setDescription("");
        viewDelegate.viewHolder.combinedchart.setDragEnabled(true);
        viewDelegate.viewHolder.combinedchart.setScaleYEnabled(false);

        Legend combinedchartLegend = viewDelegate.viewHolder.combinedchart.getLegend();
        combinedchartLegend.setEnabled(false);
        //bar x y轴
        xAxisK = viewDelegate.viewHolder.combinedchart.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(false);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setGridColor(getResources().getColor(R.color.minute_grayLine));

        axisLeftK = viewDelegate.viewHolder.combinedchart.getAxisLeft();
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawLabels(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftK.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightK = viewDelegate.viewHolder.combinedchart.getAxisRight();
        axisRightK.setDrawLabels(false);
        axisRightK.setDrawGridLines(true);
        axisRightK.setDrawAxisLine(false);
        axisRightK.setGridColor(getResources().getColor(R.color.minute_grayLine));
        viewDelegate.viewHolder.combinedchart.setDragDecelerationEnabled(true);
        viewDelegate.viewHolder.barChart.setDragDecelerationEnabled(true);
        viewDelegate.viewHolder.combinedchart.setDragDecelerationFrictionCoef(0.2f);
        viewDelegate.viewHolder.barChart.setDragDecelerationFrictionCoef(0.2f);


        // 将K线控的滑动事件传递给交易量控件
        viewDelegate.viewHolder.combinedchart.setOnChartGestureListener(new CoupleChartGestureListener(viewDelegate.viewHolder.combinedchart, new Chart[]{viewDelegate.viewHolder.barChart}));
        // 将交易量控件的滑动事件传递给K线控件
        viewDelegate.viewHolder.barChart.setOnChartGestureListener(new CoupleChartGestureListener(viewDelegate.viewHolder.barChart, new Chart[]{viewDelegate.viewHolder.combinedchart}));
        viewDelegate.viewHolder.barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.e("%%%%", h.getXIndex() + "");
                viewDelegate.viewHolder.combinedchart.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                viewDelegate.viewHolder.combinedchart.highlightValue(null);
            }
        });
        viewDelegate.viewHolder.combinedchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                viewDelegate.viewHolder.barChart.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                viewDelegate.viewHolder.barChart.highlightValue(null);
            }
        });


    }

    private float getSum(Integer a, Integer b) {

        for (int i = a; i <= b; i++) {
            sum += mData.getKLineDatas().get(i).close;
        }
        return sum;
    }

    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }


    private void setData(DataParse mData) {
        kLineDatas = mData.getKLineDatas();
        int size = kLineDatas.size();   //点的个数
        // axisLeftBar.setAxisMaxValue(mData.getVolmax());
        String unit = MyUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        // axisRightBar.setAxisMaxValue(mData.getVolmax());
        Log.e("@@@", mData.getVolmax() + "da");

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<CandleEntry> candleEntries = new ArrayList<>();
        ArrayList<Entry> line5Entries = new ArrayList<>();
        ArrayList<Entry> line10Entries = new ArrayList<>();
        ArrayList<Entry> line30Entries = new ArrayList<>();

        for (int i = 0, j = 0; i < mData.getKLineDatas().size(); i++, j++) {
            xVals.add(mData.getKLineDatas().get(i).date + "");
            barEntries.add(new BarEntry(mData.getKLineDatas().get(i).vol, i));
            candleEntries.add(new CandleEntry(i, mData.getKLineDatas().get(i).high, mData.getKLineDatas().get(i).low, mData.getKLineDatas().get(i).open, mData.getKLineDatas().get(i).close));
            if (i >= 4) {
                sum = 0;
                line5Entries.add(new Entry(getSum(i - 4, i) / 5, i));
            }
            if (i >= 9) {
                sum = 0;
                line10Entries.add(new Entry(getSum(i - 9, i) / 10, i));
            }
            if (i >= 29) {
                sum = 0;
                line30Entries.add(new Entry(getSum(i - 29, i) / 30, i));
            }
        }
        barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setBarSpacePercent(20); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(getResources().getColor(R.color.marker_line_bg));
        barDataSet.setDrawValues(false);

        List<Integer> list = new ArrayList<>();
        list.add(getResources().getColor(R.color.increasing_color));
        list.add(getResources().getColor(R.color.decreasing_color));
        barDataSet.setColors(list);

        BarData barData = new BarData(xVals, barDataSet);


        viewDelegate.viewHolder.barChart.setData(barData);
        final ViewPortHandler viewPortHandlerBar = viewDelegate.viewHolder.barChart.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 3;
        touchmatrix.postScale(xscale, 1f);


        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "KLine");
        candleDataSet.setDrawHorizontalHighlightIndicator(false);
        candleDataSet.setHighlightEnabled(true);

        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setShadowColorSameAsCandle(true);

        candleDataSet.setHighLightColor(Color.WHITE);
        candleDataSet.setValueTextSize(10f);
        candleDataSet.setDrawValues(false);

        candleDataSet.setDecreasingColor(getResources().getColor(R.color.decreasing_color));//设置开盘价高于收盘价的颜色
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        candleDataSet.setIncreasingColor(getResources().getColor(R.color.increasing_color));//设置开盘价地狱收盘价的颜色
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        candleDataSet.setNeutralColor(getResources().getColor(R.color.decreasing_color));//设置开盘价等于收盘价的颜色

        candleDataSet.setShadowWidth(1f);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);


        CandleData candleData = new CandleData(xVals, candleDataSet);

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        if (size >= 30) {
            sets.add(setMaLine(5, xVals, line5Entries));
            sets.add(setMaLine(10, xVals, line10Entries));
            sets.add(setMaLine(30, xVals, line30Entries));
        } else if (size >= 10 && size < 30) {
            sets.add(setMaLine(5, xVals, line5Entries));
            sets.add(setMaLine(10, xVals, line10Entries));
        } else if (size >= 5 && size < 10) {
            sets.add(setMaLine(5, xVals, line5Entries));
        }


        CombinedData combinedData = new CombinedData(xVals);
        LineData lineData = new LineData(xVals, sets);
        combinedData.setData(candleData);
        combinedData.setData(lineData);
        viewDelegate.viewHolder.combinedchart.setData(combinedData);
        viewDelegate.viewHolder.combinedchart.moveViewToX(mData.getKLineDatas().size() - 1);
        final ViewPortHandler viewPortHandlerCombin = viewDelegate.viewHolder.combinedchart.getViewPortHandler();
        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(xVals.size()));
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        final float xscaleCombin = 3;
        matrixCombin.postScale(xscaleCombin, 1f);

        viewDelegate.viewHolder.combinedchart.moveViewToX(mData.getKLineDatas().size() - 1);
        viewDelegate.viewHolder.barChart.moveViewToX(mData.getKLineDatas().size() - 1);

        setOffset();

        /****************************************************************************************
         此处解决方法来源于viewDelegate.viewHolder.combinedchartDemo，k线图y轴显示问题，图表滑动后才能对齐的bug，希望有人给出解决方法
         (注：此bug现已修复，感谢和chenguang79一起研究)
         ****************************************************************************************/

        handler.sendEmptyMessageDelayed(0, 300);

    }

    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(Color.WHITE);
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(Color.GREEN);
        } else if (ma == 10) {
            lineDataSetMa.setColor(Color.GRAY);
        } else {
            lineDataSetMa.setColor(Color.YELLOW);
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = viewDelegate.viewHolder.combinedchart.getViewPortHandler().offsetLeft();
        float barLeft = viewDelegate.viewHolder.barChart.getViewPortHandler().offsetLeft();
        float lineRight = viewDelegate.viewHolder.combinedchart.getViewPortHandler().offsetRight();
        float barRight = viewDelegate.viewHolder.barChart.getViewPortHandler().offsetRight();
        float barBottom = viewDelegate.viewHolder.barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            viewDelegate.viewHolder.barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            viewDelegate.viewHolder.combinedchart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            viewDelegate.viewHolder.barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            viewDelegate.viewHolder.combinedchart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        viewDelegate.viewHolder.barChart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
    }
    @Override
    protected Class<DemoDelegate> getDelegateClass() {
        return DemoDelegate.class;
    }

    @Override
    public DemoBinder getDataBinder(DemoDelegate viewDelegate) {
        return new DemoBinder(viewDelegate);
    }




    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
