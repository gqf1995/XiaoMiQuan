package com.xiaomiquan.widget.chart;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
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
import com.xiaomiquan.utils.UserSet;

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

    protected KCombinedChart mChartKline;
    protected KCombinedChart mChartVolume;

    Context mContext;

    OnClick onClick;

    public DataParse getmData() {
        return mData;
    }

    public interface OnClick {
        void click(int xPosition);
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public void setData(Context context, DataParse data, KCombinedChart chartKline, KCombinedChart chartVolume) {
        Log.i("KlineDraw", "setData");

        mData = data;
        mContext = context;
        mChartKline = chartKline;
        mChartVolume = chartVolume;

        chartKline.setDrawHeightAndLow(true);

        setKLineDatas();
        initChartKline();
        initChartVolume();
        setChartListener();

        setMarkerViewButtom(mData, chartKline);
        setMarkerView(mData, chartVolume);

        setKLineByChart(mChartKline);
        setVolumeByChart(mChartVolume);


        mChartKline.setAutoScaleMinMaxEnabled(true);
        mChartVolume.setAutoScaleMinMaxEnabled(true);
        setOffset();
        mChartKline.moveViewToX(kLineDatas.size() - 1);
        mChartVolume.moveViewToX(kLineDatas.size() - 1);

        mChartKline.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                if (onClick != null) {
                    onClick.click(position);
                }
            }
        });
        mChartVolume.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                if (onClick != null) {
                    onClick.click(position);
                }
            }
        });
    }

    public void cleanData() {
        mChartKline.clear();
        mChartVolume.clear();
    }

    public void updata(List<KLineBean> lineBeans) {
        if (lineBeans.size() > 0) {
            //mData.initLineDatas(lineBeans);
            kLineDatas = mData.getKLineDatas();

            //更新 当前 到数第一根 k线
            KLineBean kLineBean = new KLineBean();
            kLineBean.date = kLineDatas.get(kLineDatas.size() - 1).date;
            kLineBean.timestamp = kLineDatas.get(kLineDatas.size() - 1).timestamp;
            kLineBean.open = kLineDatas.get(kLineDatas.size() - 1).open;
            kLineBean.close = kLineDatas.get(kLineDatas.size() - 1).close;
            kLineBean.low = kLineDatas.get(kLineDatas.size() - 1).low;
            kLineBean.volume = kLineDatas.get(kLineDatas.size() - 1).volume;
            kLineBean.high = kLineDatas.get(kLineDatas.size() - 1).high;
            kLineBean.close = lineBeans.get(0).close;

            if (kLineBean.high.compareTo(lineBeans.get(0).high) == -1) {
                kLineBean.high = lineBeans.get(0).high;
            }
            if (kLineBean.low.compareTo(lineBeans.get(0).low) == 1) {
                kLineBean.low = lineBeans.get(0).low;
            }

            kLineDatas.remove(kLineDatas.size() - 1);
            kLineDatas.add(kLineBean);

            mData.initKLineMA(kLineDatas);
            mData.initVlumeMA(kLineDatas);


            updatLastKline(kLineBean);
            updatLastVolume(kLineBean);


            if (lineBeans.size() > 1) {
                for (int i = 1; i < lineBeans.size(); i++) {
                    kLineDatas.add(lineBeans.get(i));
                }

                mData.initKLineMA(kLineDatas);
                mData.initVlumeMA(kLineDatas);

                for (int i = 1; i < lineBeans.size(); i++) {
                    addVolumeData(lineBeans.size() - i);
                    addKlineData(lineBeans.size() - i);
                    mData.getXVals().add(lineBeans.get(i).date);
                }
            }


            mChartKline.setAutoScaleMinMaxEnabled(true);
            mChartVolume.setAutoScaleMinMaxEnabled(true);

            mChartKline.notifyDataSetChanged();
            mChartVolume.notifyDataSetChanged();

            mChartKline.invalidate();
            mChartVolume.invalidate();

            setOffset();

        }

    }

    private void updatLastKline(KLineBean kLineBean) {
        CandleData candleData = mChartKline.getCandleData();
        LineData lineData = mChartKline.getLineData();

        if (candleData != null) {
            int indexLast = getLastDataSetIndex(candleData);
            CandleDataSet lastSet = (CandleDataSet) candleData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createCandleDataSet();
                candleData.addDataSet(lastSet);
            }
            //count = lastSet.getEntryCount();
            // 位最后一个DataSet添加entry
            KLog.i("chart", kLineBean.toString());
            CandleEntry candleEntry = new CandleEntry(lastSet.getYVals().size() - 1, kLineBean.high.floatValue(), kLineBean.low.floatValue(), kLineBean.open.floatValue(), kLineBean.close.floatValue());
            //candleData.addEntry(candleEntry, indexLast);
            //candleData.removeEntry(mData.getKLineDatas().size() - 1, indexLast);
            //            lastSet.getYVals().remove(lastSet.getYVals().size() - 1);
            //            lastSet.getYVals().add(candleEntry);
            candleData.removeEntry(lastSet.getYVals().size() - 1, indexLast);//lastSet.getYVals().size() - 1, indexLast);
            candleData.addEntry(candleEntry, indexLast);
            //candleData.addEntry(candleEntry, indexLast);
        }

        if (lineData != null) {
            LineDataSet lineDataSet5 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
            LineDataSet lineDataSet10 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;
            LineDataSet lineDataSet20 = (LineDataSet) lineData.getDataSetByIndex(2);//二十日均线;
            LineDataSet lineDataSet30 = (LineDataSet) lineData.getDataSetByIndex(3);//三十日均线;

            if (lineDataSet5 != null) {
                //mData.getMa5DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 5), count));
                //lineData.addEntry(mData.getMa5DataL().get(mData.getMa5DataL().size() - index), 0);
                // lineDataSet5.getYVals().remove(lineDataSet5.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa5DataL().size() - 1, 0);
                lineData.addEntry(mData.getMa5DataL().get(mData.getMa5DataL().size() - 1), 0);
            }

            if (lineDataSet10 != null) {
                // mData.getMa10DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 10), count));
                //lineDataSet10.getYVals().remove(lineDataSet10.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa10DataL().size() - 1, 1);
                lineData.addEntry(mData.getMa10DataL().get(mData.getMa10DataL().size() - 1), 1);
            }

            if (lineDataSet20 != null) {
                //mData.getMa20DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 20), count));
                //lineDataSet20.getYVals().remove(lineDataSet20.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa20DataL().size() - 1, 2);
                lineData.addEntry(mData.getMa20DataL().get(mData.getMa20DataL().size() - 1), 2);
            }

            if (lineDataSet30 != null) {
                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
                //lineDataSet30.getYVals().remove(lineDataSet30.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa30DataL().size() - 1, 3);
                lineData.addEntry(mData.getMa30DataL().get(mData.getMa30DataL().size() - 1), 3);
            }
        }
    }

    private void updatLastVolume(KLineBean kLineBean) {
        BarData barData = mChartVolume.getBarData();
        LineData lineData = mChartVolume.getLineData();
        if (barData != null) {
            int indexLast = getLastDataSetIndex(barData);
            BarDataSet lastSet = (BarDataSet) barData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createBarDataSet();
                barData.addDataSet(lastSet);
            }

            //lastSet.getYVals().remove(lastSet.getYVals().size() - 1);
            barData.removeEntry(lastSet.getYVals().size() - 1, indexLast);//astSet.getYVals().size() - 1, indexLast);
            //lastSet.getYVals().add(new BarEntry(kLineBean.volume.floatValue(), lastSet.getYVals().size()));
            //barData.addEntry(new BarEntry(kLineBean.volume.floatValue(), lastSet.getYVals().size() - 1), indexLast);
            barData.addEntry(new BarEntry(kLineBean.volume.floatValue(), barData.getYValCount() - 1), indexLast);

        }

        if (lineData != null) {
            LineDataSet lineDataSet5 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
            LineDataSet lineDataSet10 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;
            LineDataSet lineDataSet20 = (LineDataSet) lineData.getDataSetByIndex(2);//二十日均线;
            LineDataSet lineDataSet30 = (LineDataSet) lineData.getDataSetByIndex(3);//三十日均线;

            if (lineDataSet5 != null) {
                //mData.getMa5DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 5), count));
                // lineDataSet5.getYVals().remove(lineDataSet5.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa5DataV().size() - 1, 0);
                lineData.addEntry(mData.getMa5DataV().get(mData.getMa5DataV().size() - 1), 0);
            }

            if (lineDataSet10 != null) {
                //mData.getMa10DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 10), count));
                //lineDataSet10.getYVals().remove(lineDataSet10.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa10DataV().size() - 1, 1);
                lineData.addEntry(mData.getMa10DataV().get(mData.getMa10DataV().size() - 1), 1);
            }

            if (lineDataSet20 != null) {
                //mData.getMa20DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 20), count));
                //lineDataSet20.getYVals().remove(lineDataSet20.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa20DataV().size() - 1, 2);
                lineData.addEntry(mData.getMa20DataV().get(mData.getMa20DataV().size() - 1), 2);
            }

            if (lineDataSet30 != null) {
                //mData.getMa30DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 30), count));
                //lineDataSet30.getYVals().remove(lineDataSet30.getYVals().size() - 1);
                lineData.removeEntry(mData.getMa30DataV().size() - 1, 3);
                lineData.addEntry(mData.getMa30DataV().get(mData.getMa30DataV().size() - 1), 3);
            }
        }

    }

    private void addKlineData(int index) {
        CandleData candleData = mChartKline.getCandleData();
        LineData lineData = mChartKline.getLineData();

        int count = 0;
        int i = kLineDatas.size() - index;
        String xVals = kLineDatas.get(kLineDatas.size() - index).date;//mData.getXVals().get(mData.getXVals().size() - index);
        if (candleData != null) {
            int indexLast = getLastDataSetIndex(candleData);
            CandleDataSet lastSet = (CandleDataSet) candleData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createCandleDataSet();
                candleData.addDataSet(lastSet);
            }
            //count = lastSet.getEntryCount();
            count = i;

            // 位最后一个DataSet添加entry
            KLineBean kLineBean = kLineDatas.get(i);

            KLog.i("chart", kLineBean.toString());

            CandleEntry candleEntry = new CandleEntry(mData.getKLineDatas().size() - index, kLineBean.high.floatValue(), kLineBean.low.floatValue(), kLineBean.open.floatValue(), kLineBean.close.floatValue());
            //combinedData.addEntry(candleEntry, indexLast);
            candleData.addEntry(candleEntry, indexLast);
            //candleData.addXValue(xVals);
            //mChartKline.setData();
            //combinedData.addEntry(mData.getCandleEntries().get(mData.getCandleEntries().size() - index - 1), indexLast);

        }

        if (lineData != null) {
            LineDataSet lineDataSet5 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
            LineDataSet lineDataSet10 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;
            LineDataSet lineDataSet20 = (LineDataSet) lineData.getDataSetByIndex(2);//二十日均线;
            LineDataSet lineDataSet30 = (LineDataSet) lineData.getDataSetByIndex(3);//三十日均线;

            if (lineDataSet5 != null) {
                //mData.getMa5DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 5), count));
                lineData.addEntry(mData.getMa5DataL().get(mData.getMa5DataL().size() - index), 0);
            }

            if (lineDataSet10 != null) {
                // mData.getMa10DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 10), count));
                lineData.addEntry(mData.getMa10DataL().get(mData.getMa10DataL().size() - index), 1);
            }

            if (lineDataSet20 != null) {
                //mData.getMa20DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 20), count));
                lineData.addEntry(mData.getMa20DataL().get(mData.getMa20DataL().size() - index), 2);
            }

            if (lineDataSet30 != null) {
                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
                lineData.addEntry(mData.getMa30DataL().get(mData.getMa30DataL().size() - index), 3);
            }
        }


    }

    private void addVolumeData(int index) {
        BarData barData = mChartVolume.getBarData();
        LineData lineData = mChartVolume.getLineData();

        int count = 0;

        int i = kLineDatas.size() - index;
        String xVals = kLineDatas.get(kLineDatas.size() - index).date;//mData.getXVals().get(mData.getXVals().size() - index);
        if (barData != null) {
            int indexLast = getLastDataSetIndex(barData);
            BarDataSet lastSet = (BarDataSet) barData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createBarDataSet();
                barData.addDataSet(lastSet);
            }
            //count = lastSet.getEntryCount();
            count = i;
            //barData.addEntry(new BarEntry(count, kLineDatas.get(i).high, kLineDatas.get(i).low, kLineDatas.get(i).open, kLineDatas.get(i).close, kLineDatas.get(i).volume), indexLast);
            barData.addEntry(new BarEntry(mData.getKLineDatas().get(mData.getKLineDatas().size() - index).volume.floatValue(), mData.getKLineDatas().size() - index), indexLast);
            //barData.addEntry(mData.getBarEntries().get(mData.getBarEntries().size() - index), indexLast);
            // barData.addXValue(xVals);
        }

        if (lineData != null) {
            LineDataSet lineDataSet5 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
            LineDataSet lineDataSet10 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;
            LineDataSet lineDataSet20 = (LineDataSet) lineData.getDataSetByIndex(2);//二十日均线;
            LineDataSet lineDataSet30 = (LineDataSet) lineData.getDataSetByIndex(3);//三十日均线;

            if (lineDataSet5 != null) {
                //mData.getMa5DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 5), count));
                lineData.addEntry(mData.getMa5DataV().get(mData.getMa5DataV().size() - index), 0);
            }

            if (lineDataSet10 != null) {
                //mData.getMa10DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 10), count));
                lineData.addEntry(mData.getMa10DataV().get(mData.getMa10DataV().size() - index), 1);
            }

            if (lineDataSet20 != null) {
                //mData.getMa20DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 20), count));
                lineData.addEntry(mData.getMa20DataV().get(mData.getMa20DataV().size() - index), 2);
            }

            if (lineDataSet30 != null) {
                //mData.getMa30DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 30), count));
                lineData.addEntry(mData.getMa30DataV().get(mData.getMa30DataV().size() - index), 3);
            }
        }


    }

    /**
     * 获取最后一个LineDataSet的索引
     */
    private int getDataSetIndexCount(LineData lineData) {
        int dataSetCount = lineData.getDataSetCount();
        return dataSetCount;
    }

    /**
     * 获取最后一个CandleDataSet的索引
     */
    private int getLastDataSetIndex(BarData barData) {
        int dataSetCount = barData.getDataSetCount();
        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
    }

    /**
     * 获取最后一个CandleDataSet的索引
     */
    private int getLastDataSetIndex(CandleData candleData) {
        int dataSetCount = candleData.getDataSetCount();
        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
    }

    private CandleDataSet createCandleDataSet() {
        CandleDataSet dataSet = new CandleDataSet(null, "DataSet 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setValueTextSize(12f);

        return dataSet;
    }

    private LineDataSet createLineDataSet() {
        LineDataSet dataSet = new LineDataSet(null, "DataSet 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setValueTextSize(12f);

        return dataSet;
    }

    private BarDataSet createBarDataSet() {
        BarDataSet dataSet = new BarDataSet(null, "DataSet 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setValueTextSize(12f);

        return dataSet;
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
        mChartKline.setExtraOffsets(0f, 0f, 0f, 0f);


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
        axisLeftKline.setDrawGridLines(false);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(CommonUtils.getColor(R.color.color_font4));
        axisLeftKline.setTextSize(10);
        //        axisLeftKline.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(5, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftKline.setSpaceTop(10f);//距离顶部留白

        axisRightKline = mChartKline.getAxisLeft();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布


        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);

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
        mChartVolume.setExtraOffsets(0f, 0f, 0f, 0f);
        mChartVolume.setDrawHighlightArrow(true);

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
        xAxisVolume.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftVolume = mChartVolume.getAxisRight();
        //axisLeftVolume.setAxisMinValue(0);//设置Y轴坐标最小为多少
        //        axisLeftVolume.setShowOnlyMinMax(true);//设置Y轴坐标最小为多少
        axisLeftVolume.setDrawGridLines(false);
        axisLeftVolume.setDrawAxisLine(false);
        axisLeftVolume.setDrawLabels(true);
        axisLeftVolume.setTextSize(10);
        axisLeftVolume.enableGridDashedLine(10f, 10f, 0f);
        axisLeftVolume.setTextColor(CommonUtils.getColor(R.color.color_font4));
        //        axisLeftVolume.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftVolume.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftVolume.setSpaceTop(10f);//距离顶部留白
        axisLeftVolume.setDrawZeroLine(false);
        //        axisLeftVolume.setSpaceBottom(0);//距离顶部留白

        axisRightVolume = mChartVolume.getAxisLeft();
        axisRightVolume.setDrawLabels(false);
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setDrawAxisLine(false);

        axisRightVolume.setAxisMinValue(0);
        axisLeftVolume.setAxisMinValue(0);

        mChartVolume.setDragDecelerationEnabled(true);
        mChartVolume.setDragDecelerationFrictionCoef(0.2f);

    }

    private void setKLineByChart(CombinedChart combinedChart) {
        CandleDataSet set = new CandleDataSet(mData.getCandleEntries(), "");
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(1f);
        set.setValueTextSize(10f);
        set.setDecreasingColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));//设置开盘价高于收盘价的颜色
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));//设置开盘价地狱收盘价的颜色
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));//设置开盘价等于收盘价的颜色
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


        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(candleData);
        combinedData.setData(lineData);

        combinedChart.setData(combinedData);
        combinedChart.setDrawHighlightArrow(true);

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
        list.add(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        list.add(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
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
        combinedChart.setDrawHighlightArrow(true);

        setHandler(combinedChart);
    }


    private void setHandler(CombinedChart combinedChart) {
        final ViewPortHandler viewPortHandlerBar = combinedChart.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(MyUtils.culcMaxscale(mData.getXVals().size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 20;
        touchmatrix.postScale(xscale, 1f);
    }

    private void setMarkerViewButtom(DataParse mData, KCombinedChart combinedChart) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(mContext, R.layout.mymarkerview);
        MyHMarkerView hMarkerView = new MyHMarkerView(mContext, R.layout.mymarkerview_line);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(mContext, R.layout.mymarkerview);
        combinedChart.setMarker(leftMarkerView, bottomMarkerView, hMarkerView, mData);
    }

    private void setMarkerView(DataParse mData, KCombinedChart combinedChart) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(mContext, R.layout.mymarkerview);
        MyHMarkerView hMarkerView = new MyHMarkerView(mContext, R.layout.mymarkerview_line);
        combinedChart.setMarker(leftMarkerView, hMarkerView, mData);
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
                mChartKline.highlightValue(null);
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
                mChartVolume.highlightValue(null);
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
            mChartVolume.setExtraRightOffset(offsetRight);
            transRight = kbRight;
        }
        mChartKline.setViewPortOffsets(transLeft, 15, transRight, CommonUtils.getDimensionPixelSize(R.dimen.trans_45px));
        mChartVolume.setViewPortOffsets(transLeft, 15, transRight, kbBottom);
    }

}
