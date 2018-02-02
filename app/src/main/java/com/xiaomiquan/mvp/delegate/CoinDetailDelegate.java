package com.xiaomiquan.mvp.delegate;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;

public class CoinDetailDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_detail;
    }
    public void initData(ExchangeData exchangeData) {

    }

    public static class ViewHolder {
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

        }

    }
}