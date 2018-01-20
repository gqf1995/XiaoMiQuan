package com.xiaomiquan.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class SearchCoinMarketResultDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_coin_market_result;
    }


    public static class ViewHolder {
        public View rootView;
        public RecyclerView recycler_view;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        }

    }
}