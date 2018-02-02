package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;

public class BigVListDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend;
    }

    public static class ViewHolder {
        public View rootView;
        public CommonTabLayout tl_2;
        public ViewPager vp_sliding;
        public LinearLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
            this.swipeRefreshLayout = (LinearLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}