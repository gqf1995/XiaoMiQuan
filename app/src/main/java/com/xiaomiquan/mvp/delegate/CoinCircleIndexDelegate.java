package com.xiaomiquan.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatToolbar;

public class CoinCircleIndexDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_circle_index;
    }


    public static class ViewHolder {
        public View rootView;
        public CommonTabLayout tl_2;
        public ViewPager viewpager;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        }

    }
}