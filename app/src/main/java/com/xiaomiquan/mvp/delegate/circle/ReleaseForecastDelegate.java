package com.xiaomiquan.mvp.delegate.circle;

import com.xiaomiquan.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;

import android.view.View;

public class ReleaseForecastDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_forecast;
    }


    public static class ViewHolder {
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

        }

    }
}