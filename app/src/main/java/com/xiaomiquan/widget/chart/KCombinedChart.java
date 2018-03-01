package com.xiaomiquan.widget.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.xiaomiquan.entity.bean.kline.DataParse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/12 0012.
 */

public class KCombinedChart extends CombinedChart {

    private MyLeftMarkerView myMarkerViewLeft;
    private MyHMarkerView myMarkerViewH;
    private MyBottomMarkerView myBottomMarkerView;
    private DataParse minuteHelper;
    boolean isDrawHeightAndLow = false;
    DefaultClickLinsener defaultClickLinsener;
    Canvas canvas;
    OnMaxLeftLinsener onMaxLeftLinsener;

    public void setOnMaxLeftLinsener(OnMaxLeftLinsener onMaxLeftLinsener) {
        this.onMaxLeftLinsener = onMaxLeftLinsener;
    }

    public interface OnMaxLeftLinsener {
        void onMaxLeft();
    }


    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public void setDrawHeightAndLow(boolean drawHeightAndLow) {
        isDrawHeightAndLow = drawHeightAndLow;
    }

    public KCombinedChart(Context context) {
        super(context);
    }

    public KCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyHMarkerView markerH, DataParse minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.myMarkerViewH = markerH;
        this.minuteHelper = minuteHelper;
        setDrawMarkerViews(true);
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, DataParse minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerBottom;
        this.minuteHelper = minuteHelper;
        setDrawMarkerViews(true);
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, MyHMarkerView markerH, DataParse minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerBottom;
        this.myMarkerViewH = markerH;
        this.minuteHelper = minuteHelper;
        setDrawMarkerViews(true);
    }

    private Entry getEntry(Highlight highlight) {
        List<ChartData> dataObjects = mData.getAllData();

        if (highlight.getDataIndex() >= dataObjects.size())
            return null;

        ChartData data = dataObjects.get(highlight.getDataIndex());

        if (highlight.getDataSetIndex() >= data.getDataSetCount())
            return null;
        else {
            return data.getDataSetByIndex(highlight.getDataSetIndex()).getEntryForXIndex(highlight.getXIndex());
        }
    }


    public void drawMarkerView() {
        int lowestVisibleXIndex = this.getLowestVisibleXIndex();
        if (onMaxLeftLinsener != null) {
            if (lowestVisibleXIndex == 0) {
                onMaxLeftLinsener.onMaxLeft();
            }
        }
        if (!mDrawMarkerViews || !valuesToHighlight())
            return;
        for (int i = 0; i < mIndicesToHighlight.length; i++) {
            Highlight highlight = mIndicesToHighlight[i];
            int xIndex = mIndicesToHighlight[i].getXIndex();
            int dataSetIndex = mIndicesToHighlight[i].getDataSetIndex();
            float deltaX = mXAxis != null
                    ? mXAxis.mAxisRange
                    : ((mData == null ? 0.f : mData.getXValCount()) - 1.f);
            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {
                if (mData.getAllData().size() < i) {
                    return;
                }
                Entry e = getEntry(mIndicesToHighlight[i]); //mData.getEntryForHighlight(mIndicesToHighlight[i]);
                // make sure entry not null
                if (e == null || e.getXIndex() != mIndicesToHighlight[i].getXIndex())
                    continue;
                float[] pos = getMarkerPosition(e, highlight);
                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;

                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(null, mIndicesToHighlight[i].getXIndex(), mIndicesToHighlight[i]);
                }

                String yChartMax = this.getYChartMax() + "";
                String yChartMin = this.getYChartMin() + "";

                String value = minuteHelper.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).close.toString() + "";

                BigDecimal bigDecimal = new BigDecimal(yChartMin);
                BigDecimal valueB = new BigDecimal(value).subtract(bigDecimal);
                BigDecimal yChartMaxB = new BigDecimal(yChartMax).subtract(bigDecimal);

                String hightS = (this.getMeasuredHeight() - this.getViewPortHandler().offsetBottom() - this.getViewPortHandler().offsetTop()) + "";
                BigDecimal divB = valueB.multiply(new BigDecimal(hightS)).divide(yChartMaxB, 10, BigDecimal.ROUND_HALF_UP);

                float height = this.getMeasuredHeight() - divB.floatValue() - this.getViewPortHandler().offsetBottom();


                if (null != myMarkerViewH) {
                    myMarkerViewH.refreshContent(e, mIndicesToHighlight[i]);
                    int width = (int) mViewPortHandler.contentWidth();
                    myMarkerViewH.setTvWidth(width);
                    myMarkerViewH.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myMarkerViewH.layout(0, 0, width,
                            myMarkerViewH.getMeasuredHeight());

                    myMarkerViewH.draw(canvas, mViewPortHandler.contentLeft(), height);
                }

                if (null != myMarkerViewLeft) {
                    //修改标记值
                    float yValForHighlight = mIndicesToHighlight[i].getValue();
                    myMarkerViewLeft.setData(yValForHighlight);
                    myMarkerViewLeft.refreshContent(e, mIndicesToHighlight[i]);
                    myMarkerViewLeft.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
                            myMarkerViewLeft.getMeasuredHeight());
                    myMarkerViewLeft.draw(canvas, mViewPortHandler.contentRight(), height - myMarkerViewLeft.getHeight() / 2);
                }

                if (null != myBottomMarkerView) {
                    String time = minuteHelper.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).date;
                    myBottomMarkerView.setData(time);
                    myBottomMarkerView.refreshContent(e, mIndicesToHighlight[i]);
                    myBottomMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myBottomMarkerView.layout(0, 0, myBottomMarkerView.getMeasuredWidth(),
                            myBottomMarkerView.getMeasuredHeight());

                    myBottomMarkerView.draw(canvas, pos[0] - myBottomMarkerView.getWidth() / 2, mViewPortHandler.contentBottom());
                }


            }
        }
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        this.canvas = canvas;
        drawMarkerView();
    }

    @Override
    protected void init() {
        super.init();
        /*此两处不能重新示例*/
        mAxisRendererRight = new MyYAxisRenderer(mViewPortHandler, mAxisRight, mRightAxisTransformer);
    }

    @Override
    public void setData(CombinedData data) {
        super.setData(data);
        mRenderer = new CombinedChartRenderer(this, mAnimator, mViewPortHandler);
        mRenderer.initBuffers();
    }
}