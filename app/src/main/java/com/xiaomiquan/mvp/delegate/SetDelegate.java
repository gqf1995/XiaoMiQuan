package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

public class SetDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }


    public static class ViewHolder {
        public View rootView;
        public View v_status;
        public TextView tv_cache_num;
        public LinearLayout lin_clean_cache;
        public LinearLayout lin_about_us;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.tv_cache_num = (TextView) rootView.findViewById(R.id.tv_cache_num);
            this.lin_clean_cache = (LinearLayout) rootView.findViewById(R.id.lin_clean_cache);
            this.lin_about_us = (LinearLayout) rootView.findViewById(R.id.lin_about_us);
        }

    }
}