package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.xiaomiquan.R;

public class KlineSetDelegate extends BaseFragentPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kline_set;
    }


    public static class ViewHolder {
        public View rootView;
        public SwitchButton checkbox_red_sticker;
        public LinearLayout lin_set1;
        public TextView tv_select_price_model;
        public LinearLayout lin_set2;
        public LinearLayout lin_set3;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.checkbox_red_sticker = (SwitchButton) rootView.findViewById(R.id.checkbox_red_sticker);
            this.lin_set1 = (LinearLayout) rootView.findViewById(R.id.lin_set1);
            this.tv_select_price_model = (TextView) rootView.findViewById(R.id.tv_select_price_model);
            this.lin_set2 = (LinearLayout) rootView.findViewById(R.id.lin_set2);
            this.lin_set3 = (LinearLayout) rootView.findViewById(R.id.lin_set3);
        }

    }
}