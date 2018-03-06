package com.xiaomiquan.mvp.delegate;

import android.view.View;

import com.xiaomiquan.R;

/**
 * Created by 郭青枫 on 2017/11/16.
 */

public class CustomerServiceActDelegate extends IMDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    public void ImError() {

    }

    @Override
    public void ImSuccess() {

    }

    public static class ViewHolder {
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
        }

    }
}
