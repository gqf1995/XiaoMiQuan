package com.xiaomiquan.widget.chart;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
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
import com.xiaomiquan.mpchart.CoupleChartGestureListener;
import com.xiaomiquan.mpchart.VolFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/11 0011.
 */

public class KlineDraw {
    //X轴标签的类
    protected XAxis xAxisKline, xAxisVolume;
    //Y轴左侧的线
    protected YAxis axisLeftKline, axisLeftVolume;
    //Y轴右侧的线
    protected YAxis axisRightKline, axisRightVolume;

    //解析数据
    private DataParse mData;
    //K线图数据
    private ArrayList<KLineBean> kLineDatas;

    protected CombinedChart mChartKline;
    protected CombinedChart mChartVolume;

    public void setData(DataParse data, CombinedChart chartKline, CombinedChart chartVolume) {
        mData = data;
        mChartKline = chartKline;
        mChartVolume = chartVolume;
        setKLineDatas();

        initChartKline();
        initChartVolume();
        setChartListener();

        setKLineByChart(mChartKline);
        setVolumeByChart(mChartVolume);

        mChartKline.moveViewToX(kLineDatas.size() - 1);
        mChartVolume.moveViewToX(kLineDatas.size() - 1);
        setOffset();
    }

    private void setKLineDatas() {
        kLineDatas = mData.getKLineDatas();
        mData.initLineDatas(kLineDatas);
    }

    /**
     * 初始化上面的chart公共属性
     */
    private void initChartKline() {
        mChartKline.setScaleEnabled(true);//启用图表缩放事件
        mChartKline.setDrawBorders(false);//是否绘制边线
        mChartKline.setBorderWidth(1);//边线宽度，单位dp
        mChartKline.setDragEnabled(true);//启用图表拖拽事件
        mChartKline.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartKline.setBorderColor(CommonUtils.getColor(R.color.border_color));//边线颜色
        mChartKline.setDescription("");//右下角对图表的描述信息
        mChartKline.setMinOffset(0f);
        mChartKline.setExtraOffsets(0f, 0f, 0f, 3f);

        Legend lineChartLegend = mChartKline.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        //bar x y轴
        xAxisKline = mChartKline.getXAxis();
        xAxisKline.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisKline.setTextColor(CommonUtils.getColor(R.color.color_font2));//设置字的颜色
        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mChartKline.getAxisRight();
        axisLeftKline.setDrawGridLines(true);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(CommonUtils.getColor(R.color.color_font4));
        axisLeftKline.setTextSize(10);
        //        axisLeftKline.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(8, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftKline.setSpaceTop(10f);//距离顶部留白

        axisRightKline = mChartKline.getAxisLeft();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);

        mChartKline.animateXY(2000, 2000);
    }

    /**
     * 初始化下面的chart公共属性
     */
    private void initChartVolume() {
        mChartVolume.setDrawBorders(false);  //边框是否显示
        mChartVolume.setBorderWidth(1);//边框的宽度，float类型，dp单位
        mChartVolume.setBorderColor(CommonUtils.getColor(R.color.border_color));//边框颜色
        mChartVolume.setDescription(""); //图表默认右下方的描述，参数是String对象
        mChartVolume.setDragEnabled(true);// 是否可以拖拽
        mChartVolume.setScaleYEnabled(false); //是否可以缩放 仅y轴
        mChartVolume.setMinOffset(3f);
        mChartVolume.setExtraOffsets(0f, 0f, 0f, 5f);

        Legend combinedchartLegend = mChartVolume.getLegend(); // 设置比例图标示，就是那个一组y的value的
        combinedchartLegend.setEnabled(false);//是否绘制比例图

        //bar x y轴
        xAxisVolume = mChartVolume.getXAxis();
        xAxisVolume.setEnabled(false);
        //        xAxisVolume.setDrawLabels(false); //是否显示X坐标轴上的刻度，默认是true
        //        xAxisVolume.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        //        xAxisVolume.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        //        xAxisVolume.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        //        xAxisVolume.setTextColor(CommonUtils.getColor(R.color.text_color_common));//设置字的颜色
        //        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        //        xAxisVolume.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftVolume = mChartVolume.getAxisRight();
        axisLeftVolume.setAxisMinValue(0);//设置Y轴坐标最小为多少
        //        axisLeftVolume.setShowOnlyMinMax(true);//设置Y轴坐标最小为多少
        axisLeftVolume.setDrawGridLines(true);
        axisLeftVolume.setDrawAxisLine(false);
        //        axisLeftVolume.setShowOnlyMinMax(true);
        axisLeftVolume.setDrawLabels(true);
        axisLeftVolume.setTextSize(10);
        axisLeftVolume.enableGridDashedLine(10f, 10f, 0f);
        axisLeftVolume.setTextColor(CommonUtils.getColor(R.color.color_font4));
        //        axisLeftVolume.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftVolume.setLabelCount(6, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftVolume.setSpaceTop(10f);//距离顶部留白
        //        axisLeftVolume.setSpaceBottom(0);//距离顶部留白

        axisRightVolume = mChartVolume.getAxisLeft();
        axisRightVolume.setDrawLabels(false);
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setDrawAxisLine(false);

        mChartVolume.setDragDecelerationEnabled(true);
        mChartVolume.setDragDecelerationFrictionCoef(0.2f);

        mChartVolume.animateXY(2000, 2000);
    }


    private void setChartListener() {
        // 将K线控的滑动事件传递给交易量控件
        mChartKline.setOnChartGestureListener(new CoupleChartGestureListener(mChartKline, new Chart[]{mChartVolume}));
        // 将交易量控件的滑动事件传递给K线控件
        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume, new Chart[]{mChartKline}));


        mChartKline.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                mChartVolume.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                mChartVolume.highlightValue(null);
            }
        });

        mChartVolume.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                mChartKline.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                mChartKline.highlightValue(null);
            }
        });

    }


    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = mChartKline.getViewPortHandler().offsetLeft();
        float kbLeft = mChartVolume.getViewPortHandler().offsetLeft();
        float lineRight = mChartKline.getViewPortHandler().offsetRight();
        float kbRight = mChartVolume.getViewPortHandler().offsetRight();
        float kbBottom = mChartVolume.getViewPortHandler().offsetBottom();

        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (kbLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(kbLeft - lineLeft);
            mChartKline.setExtraLeftOffset(offsetLeft);
            mChartVolume.setExtraLeftOffset(offsetLeft);
            transLeft = kbLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (kbRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(kbRight - lineRight);
            mChartKline.setExtraRightOffset(offsetRight);
            transRight = kbRight;
        }
        mChartVolume.setViewPortOffsets(transLeft, 15, transRight, kbBottom);
    }

    private void setKLineByChart(CombinedChart combinedChart) {
        CandleDataSet set = new CandleDataSet(mData.getCandleEntries(), "");
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(1f);
        set.setValueTextSize(10f);
        set.setDecreasingColor(CommonUtils.getColor(R.color.decreasing_color));//设置开盘价高于收盘价的颜色
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(CommonUtils.getColor(R.color.increasing_color));//设置开盘价地狱收盘价的颜色
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(CommonUtils.getColor(R.color.decreasing_color));//设置开盘价等于收盘价的颜色
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(1f);
        set.setHighLightColor(CommonUtils.getColor(R.color.color_font2));
        set.setDrawValues(false);
        set.setValueTextColor(CommonUtils.getColor(R.color.color_font2));
        CandleData candleData = new CandleData(mData.getXVals(), set);

        mData.initKLineMA(kLineDatas);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        sets.add(MyUtils.setMaLine(5, mData.getXVals(), mData.getMa5DataL()));
        sets.add(MyUtils.setMaLine(10, mData.getXVals(), mData.getMa10DataL()));
        sets.add(MyUtils.setMaLine(20, mData.getXVals(), mData.getMa20DataL()));
        sets.add(MyUtils.setMaLine(30, mData.getXVals(), mData.getMa30DataL()));

        Entry heightEntry = mData.getCandleEntries().get(mData.getCandleEntries().size() - 1);
        Entry lowEntry = mData.getCandleEntries().get(mData.getCandleEntries().size() - 1);
        for (int i = 0; i < mData.getCandleEntries().size(); i++) {
            if (heightEntry.getVal() < mData.getCandleEntries().get(mData.getCandleEntries().size() - 1 - i).getVal()) {
                heightEntry = mData.getCandleEntries().get(mData.getCandleEntries().size() - 1 - i);
            }
            if (lowEntry.getVal() > mData.getCandleEntries().get(mData.getCandleEntries().size() - 1 - i).getVal()) {
                lowEntry = mData.getCandleEntries().get(mData.getCandleEntries().size() - 1 - i);
            }
        }
        ArrayList<Entry> heightEntries = new ArrayList<>();
        ArrayList<Entry> lowEntries = new ArrayList<>();
        heightEntries.add(heightEntry);
        lowEntries.add(lowEntry);
        LineDataSet heightLineDataSet = MyUtils.setMaLine(5, mData.getXVals(), heightEntries);
        LineDataSet lowLineDataSet = MyUtils.setMaLine(5, mData.getXVals(), lowEntries);
        heightLineDataSet.setCircleColor(CommonUtils.getColor(R.color.color_font1));
        lowLineDataSet.setCircleColor(CommonUtils.getColor(R.color.color_font1));
        heightLineDataSet.setCircleColorHole(CommonUtils.getColor(R.color.decreasing_color));
        lowLineDataSet.setCircleColorHole(CommonUtils.getColor(R.color.increasing_color));
        heightLineDataSet.setDrawCircleHole(true);
        lowLineDataSet.setDrawCircleHole(true);
        heightLineDataSet.setCircleSize(8f);
        lowLineDataSet.setCircleSize(8f);
        heightLineDataSet.setCircleHoleRadius(4f);
        lowLineDataSet.setCircleHoleRadius(4f);
        heightLineDataSet.setDrawCircles(false);
        lowLineDataSet.setDrawCircles(false);
        heightLineDataSet.setDrawValues(true);
        lowLineDataSet.setDrawValues(true);
        heightLineDataSet.setValueTextColor(CommonUtils.getColor(R.color.color_font1));
        lowLineDataSet.setValueTextColor(CommonUtils.getColor(R.color.color_font1));
        heightLineDataSet.setValueTextSize(20f);
        lowLineDataSet.setValueTextSize(20f);

        sets.add(heightLineDataSet);
        sets.add(lowLineDataSet);


        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        combinedChart.setData(combinedData);
        setHandler(combinedChart);
    }

    private void setVolumeByChart(CombinedChart combinedChart) {
        String unit = MyUtils.getVolUnit(mData.getVolmax());
        String wan = CommonUtils.getString(R.string.wan_unit);
        String yi = CommonUtils.getString(R.string.yi_unit);
        int u = 1;
        if (wan.equals(unit)) {
            u = 4;
        } else if (yi.equals(unit)) {
            u = 8;
        }
        combinedChart.getAxisLeft().setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        //        combinedChart.getAxisLeft().setAxisMaxValue(mData.getVolmax());
        Log.e("@@@", mData.getVolmax() + "da");

        BarDataSet set = new BarDataSet(mData.getBarEntries(), "成交量");
        set.setBarSpacePercent(20); //bar空隙
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(CommonUtils.getColor(R.color.color_font2));
        set.setDrawValues(false);

        List<Integer> list = new ArrayList<>();
        list.add(CommonUtils.getColor(R.color.increasing_color));
        list.add(CommonUtils.getColor(R.color.decreasing_color));
        set.setColors(list);
        BarData barData = new BarData(mData.getXVals(), set);

        mData.initVlumeMA(kLineDatas);
        ArrayList<ILineDataSet> sets = new ArrayList<>();

        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        sets.add(MyUtils.setMaLine(5, mData.getXVals(), mData.getMa5DataV()));
        sets.add(MyUtils.setMaLine(10, mData.getXVals(), mData.getMa10DataV()));
        sets.add(MyUtils.setMaLine(20, mData.getXVals(), mData.getMa20DataV()));
        sets.add(MyUtils.setMaLine(30, mData.getXVals(), mData.getMa30DataV()));

        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(barData);
        combinedData.setData(lineData);
        combinedChart.setData(combinedData);
        setHandler(combinedChart);
    }


    private void setHandler(CombinedChart combinedChart) {
        final ViewPortHandler viewPortHandlerBar = combinedChart.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(MyUtils.culcMaxscale(mData.getXVals().size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 3;
        touchmatrix.postScale(xscale, 1f);
    }


    @NonNull
    private LineDataSet setMACDMaLine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);

        //DEA
        if (type == 0) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma5));
        } else {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma10));
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }

    @NonNull
    private LineDataSet setKDJMaLine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);

        //DEA
        if (type == 0) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma5));
        } else if (type == 1) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma10));
        } else if (type == 2) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma20));
        } else {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma30));
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }


}
