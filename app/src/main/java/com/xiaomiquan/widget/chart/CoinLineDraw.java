package com.xiaomiquan.widget.chart;

import android.graphics.Matrix;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.entity.bean.kline.KLineBean;

import java.util.ArrayList;

/**
 * Created by 郭青枫 on 2018/1/11 0011.
 */

public class CoinLineDraw {

    LineChart mLineChart;
    //X轴标签的类
    protected XAxis xAxisKline;
    //Y轴左侧的线
    protected YAxis axisLeftKline;
    //Y轴右侧的线
    protected YAxis axisRightKline;
    //解析数据
    private DataParse mData;
    //K线图数据
    private ArrayList<KLineBean> kLineDatas;

    public void setData(DataParse data, LineChart lineChart) {
        mData = data;
        mLineChart = lineChart;
        setKLineDatas();
        initChartKline();
        setKLineByChart(mLineChart);
    }

    /**
     * 初始化上面的chart公共属性
     */
    private void initChartKline() {
        mLineChart.setScaleEnabled(true);//启用图表缩放事件
        mLineChart.setDrawBorders(false);//是否绘制边线
        mLineChart.setBorderWidth(1);//边线宽度，单位dp
        mLineChart.setDragEnabled(true);//启用图表拖拽事件
        mLineChart.setScaleYEnabled(false);//启用Y轴上的缩放
        mLineChart.setBorderColor(CommonUtils.getColor(R.color.border_color));//边线颜色
        mLineChart.setDescription("");//右下角对图表的描述信息
        mLineChart.setMinOffset(0f);
        mLineChart.setExtraOffsets(0f, 0f, 20f, 3f);

        Legend lineChartLegend = mLineChart.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        //bar x y轴
        xAxisKline = mLineChart.getXAxis();
        xAxisKline.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisKline.setTextColor(CommonUtils.getColor(R.color.color_font2));//设置字的颜色
        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mLineChart.getAxisLeft();
        axisLeftKline.setDrawGridLines(true);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(CommonUtils.getColor(R.color.color_font4));
        axisLeftKline.setTextSize(10);
        //        axisLeftKline.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(6, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftKline.setSpaceBottom(10f);//距离顶部留白

        axisRightKline = mLineChart.getAxisRight();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        mLineChart.setDragDecelerationEnabled(true);
        mLineChart.setDragDecelerationFrictionCoef(0.2f);

        mLineChart.animateXY(2000, 2000);

    }

    private void setKLineByChart(LineChart lineChart) {
        mData.initVlumeMA(kLineDatas);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        LineDataSet lineDataSet = MyUtils.setMaLine(5, mData.getXVals(), mData.getMa5DataV());


        lineDataSet.setColor(CommonUtils.getColor(R.color.color_ef4bc1));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setFillColor(CommonUtils.getColor(R.color.black_transparent_10));
        lineDataSet.setFillAlpha(30);
        lineDataSet.setDrawFilled(true);


        Entry heightEntry = mData.getMa5DataV().get(mData.getMa5DataV().size() - 1);
        Entry lowEntry = mData.getMa5DataV().get(mData.getMa5DataV().size() - 1);
        for (int i = 0; i < mData.getMa5DataV().size(); i++) {
            if (heightEntry.getVal() < mData.getMa5DataV().get(mData.getMa5DataV().size() - 1 - i).getVal()) {
                heightEntry = mData.getMa5DataV().get(mData.getMa5DataV().size() - 1 - i);
            }
            if (lowEntry.getVal() > mData.getMa5DataV().get(mData.getMa5DataV().size() - 1 - i).getVal()) {
                lowEntry = mData.getMa5DataV().get(mData.getMa5DataV().size() - 1 - i);
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
        heightLineDataSet.setDrawCircles(true);
        lowLineDataSet.setDrawCircles(true);

        sets.add(heightLineDataSet);
        sets.add(lowLineDataSet);

        sets.add(lineDataSet);

        LineData lineData = new LineData(mData.getXVals(), sets);

        lineChart.setData(lineData);
        setHandler(lineChart);
        lineChart.moveViewToX(mData.getMa5DataV().size() - 1);
    }

    private void setKLineDatas() {
        kLineDatas = mData.getKLineDatas();
        mData.initLineDatas(kLineDatas);
    }

    private void setHandler(LineChart lineChart) {
        final ViewPortHandler viewPortHandlerBar = lineChart.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(MyUtils.culcMaxscale(mData.getXVals().size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 3;
        touchmatrix.postScale(xscale, 1f);
    }
}
