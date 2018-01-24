package com.xiaomiquan.mvp.delegate;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.github.mikephil.charting.charts.LineChart;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.GainsTabView;

import skin.support.widget.SkinCompatFrameLayout;
import skin.support.widget.SkinCompatLinearLayout;

public class ExchangeDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initTop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exchange2;
    }

    private void initTop() {
        viewHolder.tv_coin_type.setTextColor(CommonUtils.getColor(R.color.white));
        viewHolder.tv_coin_price.setTextColor(CommonUtils.getColor(R.color.white));
        viewHolder.tv_coin_probably.setTextColor(CommonUtils.getColor(R.color.white));
        viewHolder.tv_gains.setTextColor(CommonUtils.getColor(R.color.white));
        viewHolder.tv_gains.setText(CommonUtils.getString(R.string.str_rise));
        viewHolder.appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    viewHolder.swipeRefreshLayout.setEnabled(true);
                } else {
                    viewHolder.swipeRefreshLayout.setEnabled(false);
                }
            }
        });

    }


    public static class ViewHolder {
        public View rootView;
        public FontTextview tv_coin_type;
        public TextView tv_height;
        public TextView tv_low;
        public TextView tv_vol;
        public FontTextview tv_coin_price;
        public FontTextview tv_coin_probably;
        public FontTextview tv_gains;
        public LineChart linechart;
        public FrameLayout fl_chart;
        public CollapsingToolbarLayout collapsing_toolbar_layout;
        public TextView tv_unit;
        public GainsTabView tv_rise;
        public SkinCompatLinearLayout lin_root;
        public AppBarLayout appbar_layout;
        public RecyclerView recycler_view;
        public CoordinatorLayout coord_container;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatFrameLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_coin_type = (FontTextview) rootView.findViewById(R.id.tv_coin_type);
            this.tv_height = (TextView) rootView.findViewById(R.id.tv_height);
            this.tv_low = (TextView) rootView.findViewById(R.id.tv_low);
            this.tv_vol = (TextView) rootView.findViewById(R.id.tv_vol);
            this.tv_coin_price = (FontTextview) rootView.findViewById(R.id.tv_coin_price);
            this.tv_coin_probably = (FontTextview) rootView.findViewById(R.id.tv_coin_probably);
            this.tv_gains = (FontTextview) rootView.findViewById(R.id.tv_gains);
            this.linechart = (LineChart) rootView.findViewById(R.id.linechart);
            this.fl_chart = (FrameLayout) rootView.findViewById(R.id.fl_chart);
            this.collapsing_toolbar_layout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_layout);
            this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
            this.tv_rise = (GainsTabView) rootView.findViewById(R.id.tv_rise);
            this.lin_root = (SkinCompatLinearLayout) rootView.findViewById(R.id.lin_root);
            this.appbar_layout = (AppBarLayout) rootView.findViewById(R.id.appbar_layout);
            this.recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            this.coord_container = (CoordinatorLayout) rootView.findViewById(R.id.coord_container);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatFrameLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}