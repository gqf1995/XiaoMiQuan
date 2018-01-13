package com.xiaomiquan.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import com.zhy.view.flowlayout.TagFlowLayout;

public class SearchCoinMarketDefaultDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_coin_market_default;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_clean_history;
        public TagFlowLayout id_flowlayout;
        public LinearLayout lin_history;
        public RecyclerView recycler_view;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_clean_history = (TextView) rootView.findViewById(R.id.tv_clean_history);
            this.id_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_flowlayout);
            this.lin_history = (LinearLayout) rootView.findViewById(R.id.lin_history);
            this.recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        }

    }
}