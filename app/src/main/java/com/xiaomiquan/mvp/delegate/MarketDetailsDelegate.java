package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.AndroidUtil;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.spinnerviews.NiceSpinner;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.chart.KCombinedChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketDetailsDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    ArrayList<Fragment> fragments;
    String[] mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initCommonTabLayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_market_details;
    }

    private void initCommonTabLayout() {
        viewHolder.combinedchart.setNoDataText(CommonUtils.getString(R.string.str_chart_nodata));
        viewHolder.barchart.setNoDataText(CommonUtils.getString(R.string.str_chart_nodata));

        ViewGroup.LayoutParams layoutParams = viewHolder.lin_kline.getLayoutParams();
        layoutParams.height = AndroidUtil.getScreenW(viewHolder.rootView.getContext(), true) - AndroidUtil.getStatusBarHeight(viewHolder.rootView.getContext()) - (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px);
        viewHolder.lin_kline.setLayoutParams(layoutParams);

        List<String> dataset1 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_color));
        List<String> dataset2 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_color));
        List<String> dataset3 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_color));
        viewHolder.lin_time.attachDataSource(dataset1);
        viewHolder.lin_indicators.attachDataSource(dataset2);
        viewHolder.lin_color.attachDataSource(dataset3);
    }


    public static class ViewHolder {
        public View rootView;
        public IconFontTextview tv_left;
        public TextView tv_title;
        public IconFontTextview tv_right;
        public FontTextview tv_price;
        public FontTextview tv_rate;
        public IconFontTextview tv_rise;
        public TextView tv_volume;
        public TextView tv_highest;
        public TextView tv_minimum;
        public TextView tv_buy_one;
        public TextView tv_sell_one;
        public TextView tv_market_value;
        public TextView tv_circulation;
        public NiceSpinner lin_time;
        public NiceSpinner lin_indicators;
        public NiceSpinner lin_color;
        public TextView tv_ktime;
        public TextView tv_kopen;
        public TextView tv_kheight;
        public TextView tv_klow;
        public TextView tv_kclose;
        public TextView tv_ma7;
        public TextView tv_ma15;
        public TextView tv_ma30;
        public LinearLayout lin_ma;
        public TextView tv_krise;
        public TextView tv_kamplitude;
        public KCombinedChart combinedchart;
        public TextView tv_kvolume;
        public TextView tv_ma5;
        public TextView tv_ma10;
        public LinearLayout lin_ma2;
        public KCombinedChart barchart;
        public LinearLayout lin_discuss;
        public LinearLayout lin_global_market;
        public LinearLayout lin_information;
        public LinearLayout lin_currency_data;
        public LinearLayout lin_advance_warning;
        public LinearLayout lin_kline;
        public FrameLayout fl_bottom;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_left = (IconFontTextview) rootView.findViewById(R.id.tv_left);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_right = (IconFontTextview) rootView.findViewById(R.id.tv_right);
            this.tv_price = (FontTextview) rootView.findViewById(R.id.tv_price);
            this.tv_rate = (FontTextview) rootView.findViewById(R.id.tv_rate);
            this.tv_rise = (IconFontTextview) rootView.findViewById(R.id.tv_rise);
            this.tv_volume = (TextView) rootView.findViewById(R.id.tv_volume);
            this.tv_highest = (TextView) rootView.findViewById(R.id.tv_highest);
            this.tv_minimum = (TextView) rootView.findViewById(R.id.tv_minimum);
            this.tv_buy_one = (TextView) rootView.findViewById(R.id.tv_buy_one);
            this.tv_sell_one = (TextView) rootView.findViewById(R.id.tv_sell_one);
            this.tv_market_value = (TextView) rootView.findViewById(R.id.tv_market_value);
            this.tv_circulation = (TextView) rootView.findViewById(R.id.tv_circulation);
            this.lin_time = (NiceSpinner) rootView.findViewById(R.id.lin_time);
            this.lin_indicators = (NiceSpinner) rootView.findViewById(R.id.lin_indicators);
            this.lin_color = (NiceSpinner) rootView.findViewById(R.id.lin_color);
            this.tv_ktime = (TextView) rootView.findViewById(R.id.tv_ktime);
            this.tv_kopen = (TextView) rootView.findViewById(R.id.tv_kopen);
            this.tv_kheight = (TextView) rootView.findViewById(R.id.tv_kheight);
            this.tv_klow = (TextView) rootView.findViewById(R.id.tv_klow);
            this.tv_kclose = (TextView) rootView.findViewById(R.id.tv_kclose);
            this.tv_ma7 = (TextView) rootView.findViewById(R.id.tv_ma7);
            this.tv_ma15 = (TextView) rootView.findViewById(R.id.tv_ma15);
            this.tv_ma30 = (TextView) rootView.findViewById(R.id.tv_ma30);
            this.lin_ma = (LinearLayout) rootView.findViewById(R.id.lin_ma);
            this.tv_krise = (TextView) rootView.findViewById(R.id.tv_krise);
            this.tv_kamplitude = (TextView) rootView.findViewById(R.id.tv_kamplitude);
            this.combinedchart = (KCombinedChart) rootView.findViewById(R.id.combinedchart);
            this.tv_kvolume = (TextView) rootView.findViewById(R.id.tv_kvolume);
            this.tv_ma5 = (TextView) rootView.findViewById(R.id.tv_ma5);
            this.tv_ma10 = (TextView) rootView.findViewById(R.id.tv_ma10);
            this.lin_ma2 = (LinearLayout) rootView.findViewById(R.id.lin_ma2);
            this.barchart = (KCombinedChart) rootView.findViewById(R.id.barchart);
            this.lin_discuss = (LinearLayout) rootView.findViewById(R.id.lin_discuss);
            this.lin_global_market = (LinearLayout) rootView.findViewById(R.id.lin_global_market);
            this.lin_information = (LinearLayout) rootView.findViewById(R.id.lin_information);
            this.lin_currency_data = (LinearLayout) rootView.findViewById(R.id.lin_currency_data);
            this.lin_advance_warning = (LinearLayout) rootView.findViewById(R.id.lin_advance_warning);
            this.lin_kline = (LinearLayout) rootView.findViewById(R.id.lin_kline);
            this.fl_bottom = (FrameLayout) rootView.findViewById(R.id.fl_bottom);
        }

    }
}