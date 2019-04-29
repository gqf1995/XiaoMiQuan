package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;

public class RevenueRankingDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_revenue_ranking;
    }


    public static class ViewHolder {
        public View rootView;
        public CommonTabLayout tl;
        public LinearLayout lin_table;
        public ViewPager vp_sliding;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl = (CommonTabLayout) rootView.findViewById(R.id.tl);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
        }

    }
}