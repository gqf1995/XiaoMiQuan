package com.xiaomiquan.widget.chart;

import android.support.annotation.NonNull;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.xiaomiquan.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by loro on 2017/2/8.
 */
public class MyUtils {
    /**
     * Prevent class instantiation.
     */
    private MyUtils() {
    }

    public static String getVolUnit(float num) {

        int e = (int) Math.floor(Math.log10(num));

        if (e >= 8) {
            return "亿手";
        } else if (e >= 4) {
            return "万手";
        } else {
            return "手";
        }
    }

    public static float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    public static int getVolUnitNum(float num) {

        int e = (int) Math.floor(Math.log10(num));

        if (e >= 8) {
            return 8;
        } else if (e >= 4) {
            return 4;
        } else {
            return 1;
        }
    }

    public static String getVolUnitText(int unit, float num) {
        DecimalFormat mFormat;
        if (unit == 1) {
            mFormat = new DecimalFormat("#0");
        } else {
            mFormat = new DecimalFormat("#0.00");
        }
        num = num / unit;
        if (num == 0) {
            return "0";
        }
        return mFormat.format(num);
    }

    public static String getDecimalFormatVol(float vol) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(vol);//format 返回的是字符串
    }

    @NonNull
    public static LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            //此处 设置 显示 量图表 的 十字线 的 竖线
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(CommonUtils.getColor(R.color.color_font4));
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma5));
        } else if (ma == 7) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma7));
        } else if (ma == 10) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma10));
        } else if (ma == 20) {
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
