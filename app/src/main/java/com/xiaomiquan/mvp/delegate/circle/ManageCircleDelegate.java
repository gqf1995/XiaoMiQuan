package com.xiaomiquan.mvp.delegate.circle;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

import android.view.View;

public class ManageCircleDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_circle;
    }


    public static class ViewHolder {
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

        }

    }
}