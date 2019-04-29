package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.EditText;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.kyleduo.switchbutton.SwitchButton;
import com.xiaomiquan.R;

public class CustomRateDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_rate;
    }


    public static class ViewHolder {
        public View rootView;
        public SwitchButton checkbox_custom_rate;
        public EditText et_rate_cny;
        public EditText et_rate_jpy;
        public EditText et_rate_krw;
        public EditText et_rate_eur;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.checkbox_custom_rate = (SwitchButton) rootView.findViewById(R.id.checkbox_custom_rate);
            this.et_rate_cny = (EditText) rootView.findViewById(R.id.et_rate_cny);
            this.et_rate_jpy = (EditText) rootView.findViewById(R.id.et_rate_jpy);
            this.et_rate_krw = (EditText) rootView.findViewById(R.id.et_rate_krw);
            this.et_rate_eur = (EditText) rootView.findViewById(R.id.et_rate_eur);
        }

    }
}