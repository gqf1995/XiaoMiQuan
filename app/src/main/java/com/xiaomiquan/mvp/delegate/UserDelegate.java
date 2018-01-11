package com.xiaomiquan.mvp.delegate;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.github.mikephil.charting.charts.LineChart;
import com.xiaomiquan.R;

public class UserDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }


    public static class ViewHolder {
        public View rootView;
        public FontTextview tv_coin_type;
        public FontTextview tv_coin_name;
        public FontTextview tv_coin_market_value;
        public FontTextview tv_coin_price;
        public FontTextview tv_coin_probably;
        public FontTextview tv_gains;
        public LineChart linechart;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_coin_type = (FontTextview) rootView.findViewById(R.id.tv_coin_type);
            this.tv_coin_name = (FontTextview) rootView.findViewById(R.id.tv_coin_name);
            this.tv_coin_market_value = (FontTextview) rootView.findViewById(R.id.tv_coin_market_value);
            this.tv_coin_price = (FontTextview) rootView.findViewById(R.id.tv_coin_price);
            this.tv_coin_probably = (FontTextview) rootView.findViewById(R.id.tv_coin_probably);
            this.tv_gains = (FontTextview) rootView.findViewById(R.id.tv_gains);
            this.linechart = (LineChart) rootView.findViewById(R.id.linechart);
        }

    }
}