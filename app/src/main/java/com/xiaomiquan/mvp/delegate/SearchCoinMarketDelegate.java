package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class SearchCoinMarketDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_coin_market;
    }

    public static class ViewHolder {
        public View rootView;
        public EditText et_search;
        public TextView tv_commit;
        public FrameLayout fl_root;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_search = (EditText) rootView.findViewById(R.id.et_search);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.fl_root = (FrameLayout) rootView.findViewById(R.id.fl_root);
        }

    }
}