package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.DropDownView;
import com.xiaomiquan.widget.chart.KCombinedChart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketDetailsDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    ArrayList<Fragment> fragments;
    String[] mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    LinearLayout linearLayout;
    ExchangeData mExchangeData;
    List<String> klineTypeData;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initCommonTabLayout();
        klineTypeData = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline_show_type));

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


    }

    //没有k线
    public void noKlineView() {
        viewHolder.lin_kbg.setVisibility(View.GONE);
        viewHolder.lin_no_kline.setVisibility(View.VISIBLE);
    }

    public int selectPosition = 0;

    public void setDetailsData(int position, DataParse data) {
        //展示 1根k线数据
        selectPosition = position;
        KLineBean kLineBean = data.getKLineDatas().get(position);
        viewHolder.tv_ktime.setText(kLineBean.date);
        viewHolder.tv_kopen.setText(CommonUtils.getString(R.string.str_opening_quotation) + BigUIUtil.getinstance().bigPrice(kLineBean.open.toPlainString()) + "");
        viewHolder.tv_kheight.setText(CommonUtils.getString(R.string.str_highest) + BigUIUtil.getinstance().bigPrice(kLineBean.high.toPlainString()) + "");
        viewHolder.tv_klow.setText(CommonUtils.getString(R.string.str_minimum) + BigUIUtil.getinstance().bigPrice(kLineBean.low.toPlainString()) + "");
        viewHolder.tv_kclose.setText(CommonUtils.getString(R.string.str_closing_quotation) + BigUIUtil.getinstance().bigPrice(kLineBean.close.toPlainString()) + "");


        float v = (kLineBean.close.floatValue() / kLineBean.open.floatValue()) - 1;
        if (v > 0) {
            viewHolder.tv_krise.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
        } else {
            viewHolder.tv_krise.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        }

        viewHolder.tv_krise.setText(v + "");
        viewHolder.tv_kamplitude.setText(((kLineBean.high.floatValue() - kLineBean.low.floatValue()) / kLineBean.open.floatValue()) + "");

        viewHolder.tv_ma5.setText(BigUIUtil.getinstance().bigAmount(data.getMa5DataV().get(position).getVal() + "") + "");
        viewHolder.tv_ma10.setText(BigUIUtil.getinstance().bigAmount(data.getMa10DataV().get(position).getVal() + "") + "");
        viewHolder.tv_kvolume.setText(CommonUtils.getString(R.string.str_kvolume) + BigUIUtil.getinstance().bigAmount(data.getKLineDatas().get(position).volume.toPlainString()));


        if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 0) {
            //MA
            viewHolder.tv_ma7.setText("MA7:" + BigUIUtil.getinstance().bigPrice(data.getMa7DataL().get(position).getVal() + "") + "");
            //viewHolder.tv_ma15.setText("MA15:" + BigUIUtil.getinstance().bigPrice(data.getMa15DataL().get(position).getVal() + "") + "");
            viewHolder.tv_ma30.setText("MA30:" + BigUIUtil.getinstance().bigPrice(data.getMa30DataL().get(position).getVal() + "") + "");
            viewHolder.tv_ma7.setVisibility(View.VISIBLE);
            setKDJMaLine(3, viewHolder.tv_ma7);
            setKDJMaLine(1, viewHolder.tv_ma30);
            viewHolder.tv_ma15.setVisibility(View.GONE);
            viewHolder.tv_ma30.setVisibility(View.VISIBLE);
            viewHolder.tv_boll.setVisibility(View.GONE);
        } else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 1) {
            //EMA
            viewHolder.tv_ma7.setText("EMA7:" + BigUIUtil.getinstance().bigPrice(data.getExpmaData7().get(position).getVal() + "") + "");
            viewHolder.tv_ma15.setText("EMA30:" + BigUIUtil.getinstance().bigPrice(data.getExpmaData30().get(position).getVal() + "") + "");
            setKDJMaLine(3, viewHolder.tv_ma7);
            setKDJMaLine(1, viewHolder.tv_ma30);
            viewHolder.tv_ma7.setVisibility(View.VISIBLE);
            viewHolder.tv_ma15.setVisibility(View.VISIBLE);
            viewHolder.tv_ma30.setVisibility(View.GONE);
            viewHolder.tv_boll.setVisibility(View.GONE);
        } else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 2) {
            //BOLL
            viewHolder.tv_ma7.setText("BOLL:" + BigUIUtil.getinstance().bigPrice(data.getBollDataMB().get(position).getVal() + "") + "");
            viewHolder.tv_ma15.setText("UB:" + BigUIUtil.getinstance().bigPrice(data.getBollDataUP().get(position).getVal() + "") + "");
            viewHolder.tv_ma30.setText("LB:" + BigUIUtil.getinstance().bigPrice(data.getBollDataDN().get(position).getVal() + "") + "");
            setKDJMaLine(3, viewHolder.tv_ma7);
            setKDJMaLine(1, viewHolder.tv_ma30);
            setKDJMaLine(0, viewHolder.tv_ma30);
            viewHolder.tv_ma7.setVisibility(View.VISIBLE);
            viewHolder.tv_ma15.setVisibility(View.VISIBLE);
            viewHolder.tv_ma30.setVisibility(View.VISIBLE);
            viewHolder.tv_boll.setVisibility(View.VISIBLE);
        } else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 3) {
            //均线
            viewHolder.tv_ma7.setVisibility(View.GONE);
            viewHolder.tv_ma15.setVisibility(View.GONE);
            viewHolder.tv_ma30.setVisibility(View.GONE);
            viewHolder.tv_boll.setVisibility(View.GONE);
        }
    }

    private void setKDJMaLine(int type, TextView textView) {
        //DEA
        if (type == 0) {
            textView.setTextColor(CommonUtils.getColor(R.color.ma5));
        } else if (type == 1) {
            textView.setTextColor(CommonUtils.getColor(R.color.ma10));
        } else if (type == 2) {
            textView.setTextColor(CommonUtils.getColor(R.color.ma20));
        } else {
            textView.setTextColor(CommonUtils.getColor(R.color.ma30));
        }

    }

    public void initData(ExchangeData exchangeData) {
        viewHolder.tv_title.setText(exchangeData.getExchange());
        viewHolder.tv_subtitle.setText(exchangeData.getSymbol() + "/" + exchangeData.getUnit());
        String priceStr = BigUIUtil.getinstance().bigPrice(exchangeData.getLast());
        viewHolder.tv_volume.setText(BigUIUtil.getinstance().bigAmount(exchangeData.getVolume()));
        viewHolder.tv_highest.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getHigh()));
        viewHolder.tv_minimum.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getLow()));
        viewHolder.tv_buy_one.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getBid()));
        viewHolder.tv_sell_one.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getAsk()));


        List<String> strings = BigUIUtil.getinstance().rateTwoPrice(exchangeData.getLast(), exchangeData.getSymbol(), exchangeData.getUnit());
        if (TextUtils.isEmpty(strings.get(0))) {
            viewHolder.tv_price.setText("--");
        } else {
            viewHolder.tv_price.setText(strings.get(0));
        }
        viewHolder.tv_rate.setText(strings.get(1));
        if (TextUtils.isEmpty(strings.get(1))) {
            viewHolder.tv_rate.setVisibility(View.GONE);
        } else {
            viewHolder.tv_rate.setVisibility(View.VISIBLE);
        }
        String s = strings.get(0);
        String symbol = "";
        if (strings.get(0).contains("$") || strings.get(0).contains("¥")) {
            s = s.substring(1, strings.get(0).length());
            symbol = s.substring(0, 1);
        } else {
            s = exchangeData.getLast();
        }
        StringBuffer stringBuffer = new StringBuffer();
        String end = "";
        if (!TextUtils.isEmpty(exchangeData.getChange())) {
            if (new BigDecimal("0").compareTo(new BigDecimal(exchangeData.getChange())) == 1) {
                //跌
                stringBuffer.append("- ")
                        .append(symbol + BigUIUtil.getinstance().risePrice(s, exchangeData.getChange()))
                        .append("(")
                        .append(BigUIUtil.getinstance().changeAmount(exchangeData.getChange()))
                        .append("%) ");
                end = CommonUtils.getString(R.string.ic_Fall);
                viewHolder.tv_rise.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
            } else {
                //涨
                stringBuffer.append("+ ")
                        .append(symbol + BigUIUtil.getinstance().risePrice(s, exchangeData.getChange()))
                        .append("(+")
                        .append(BigUIUtil.getinstance().changeAmount(exchangeData.getChange()))
                        .append("%) ");
                end = CommonUtils.getString(R.string.ic_Climb);
                viewHolder.tv_rise.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
            }
        }

        viewHolder.tv_rise.setText(stringBuffer.toString() + " " + end);


        viewHolder.tv_volume.setText(BigUIUtil.getinstance().bigAmount(exchangeData.getVolume()));
        viewHolder.tv_highest.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getHigh()));
        viewHolder.tv_minimum.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getLow()));
        viewHolder.tv_buy_one.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getBid()));
        viewHolder.tv_sell_one.setText(BigUIUtil.getinstance().bigPrice(exchangeData.getAsk()));

        //动画
        if (mExchangeData != null) {
            if (!mExchangeData.getHigh().equals(exchangeData.getHigh())) {
                BigUIUtil.getinstance().anim(viewHolder.tv_highest, mExchangeData.getHigh(), exchangeData.getHigh(), CommonUtils.getColor(R.color.color_font2), exchangeData.getOnlyKey());
            }
            if (!mExchangeData.getLow().equals(exchangeData.getLow())) {
                BigUIUtil.getinstance().anim(viewHolder.tv_minimum, mExchangeData.getLow(), exchangeData.getLow(), CommonUtils.getColor(R.color.color_font2), exchangeData.getOnlyKey());
            }
            if (!mExchangeData.getBid().equals(exchangeData.getBid())) {
                BigUIUtil.getinstance().anim(viewHolder.tv_buy_one, mExchangeData.getBid(), exchangeData.getBid(), CommonUtils.getColor(R.color.color_font2), exchangeData.getOnlyKey());
            }
            if (!mExchangeData.getAsk().equals(exchangeData.getAsk())) {
                BigUIUtil.getinstance().anim(viewHolder.tv_sell_one, mExchangeData.getAsk(), exchangeData.getAsk(), CommonUtils.getColor(R.color.color_font2), exchangeData.getOnlyKey());
            }
            List<String> stringsold = BigUIUtil.getinstance().rateTwoPrice(mExchangeData.getLast(), exchangeData.getSymbol(), exchangeData.getUnit());
            if (!stringsold.get(0).equals(strings.get(0))) {
                BigUIUtil.getinstance().anim(viewHolder.tv_price, stringsold.get(0), strings.get(0), CommonUtils.getColor(R.color.color_font1), exchangeData.getOnlyKey());
            }
            if (!stringsold.get(1).equals(strings.get(1))) {
                BigUIUtil.getinstance().anim(viewHolder.tv_rate, stringsold.get(1), strings.get(1), CommonUtils.getColor(R.color.color_font3), exchangeData.getOnlyKey());
            }
        }
        mExchangeData = exchangeData;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_title;
        public TextView tv_subtitle;
        public FontTextview tv_price;
        public FontTextview tv_rate;
        public IconFontTextview tv_rise;
        public TextView tv_highest;
        public TextView tv_minimum;
        public TextView tv_volume;
        public TextView tv_buy_one;
        public TextView tv_sell_one;
        public TextView tv_market_value;
        public TextView tv_circulation;
        public DropDownView lin_time;
        public DropDownView lin_indicators;
        public DropDownView lin_color;
        public TextView tv_ktime;
        public TextView tv_kopen;
        public TextView tv_kheight;
        public TextView tv_klow;
        public TextView tv_kclose;
        public TextView tv_boll;
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
        public LinearLayout lin_kbg;
        public LinearLayout lin_no_kline;
        public LinearLayout lin_global_market;
        public LinearLayout lin_currency_data;
        public LinearLayout lin_simulation;
        public IconFontTextview tv_icon_add;
        public TextView tv_add;
        public LinearLayout lin_add;
        public LinearLayout lin_advance_warning;
        public LinearLayout lin_kline;
        public FrameLayout fl_bottom;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_subtitle = (TextView) rootView.findViewById(R.id.tv_subtitle);
            this.tv_price = (FontTextview) rootView.findViewById(R.id.tv_price);
            this.tv_rate = (FontTextview) rootView.findViewById(R.id.tv_rate);
            this.tv_rise = (IconFontTextview) rootView.findViewById(R.id.tv_rise);
            this.tv_highest = (TextView) rootView.findViewById(R.id.tv_highest);
            this.tv_minimum = (TextView) rootView.findViewById(R.id.tv_minimum);
            this.tv_volume = (TextView) rootView.findViewById(R.id.tv_volume);
            this.tv_buy_one = (TextView) rootView.findViewById(R.id.tv_buy_one);
            this.tv_sell_one = (TextView) rootView.findViewById(R.id.tv_sell_one);
            this.tv_market_value = (TextView) rootView.findViewById(R.id.tv_market_value);
            this.tv_circulation = (TextView) rootView.findViewById(R.id.tv_circulation);
            this.lin_time = (DropDownView) rootView.findViewById(R.id.lin_time);
            this.lin_indicators = (DropDownView) rootView.findViewById(R.id.lin_indicators);
            this.lin_color = (DropDownView) rootView.findViewById(R.id.lin_color);
            this.tv_ktime = (TextView) rootView.findViewById(R.id.tv_ktime);
            this.tv_kopen = (TextView) rootView.findViewById(R.id.tv_kopen);
            this.tv_kheight = (TextView) rootView.findViewById(R.id.tv_kheight);
            this.tv_klow = (TextView) rootView.findViewById(R.id.tv_klow);
            this.tv_kclose = (TextView) rootView.findViewById(R.id.tv_kclose);
            this.tv_boll = (TextView) rootView.findViewById(R.id.tv_boll);
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
            this.lin_kbg = (LinearLayout) rootView.findViewById(R.id.lin_kbg);
            this.lin_no_kline = (LinearLayout) rootView.findViewById(R.id.lin_no_kline);
            this.lin_global_market = (LinearLayout) rootView.findViewById(R.id.lin_global_market);
            this.lin_currency_data = (LinearLayout) rootView.findViewById(R.id.lin_currency_data);
            this.lin_simulation = (LinearLayout) rootView.findViewById(R.id.lin_simulation);
            this.tv_icon_add = (IconFontTextview) rootView.findViewById(R.id.tv_icon_add);
            this.tv_add = (TextView) rootView.findViewById(R.id.tv_add);
            this.lin_add = (LinearLayout) rootView.findViewById(R.id.lin_add);
            this.lin_advance_warning = (LinearLayout) rootView.findViewById(R.id.lin_advance_warning);
            this.lin_kline = (LinearLayout) rootView.findViewById(R.id.lin_kline);
            this.fl_bottom = (FrameLayout) rootView.findViewById(R.id.fl_bottom);
        }

    }
}