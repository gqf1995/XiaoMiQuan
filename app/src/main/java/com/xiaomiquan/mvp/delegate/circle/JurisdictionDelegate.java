package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

public class JurisdictionDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jurisdiction;
    }


    public static class ViewHolder {
        public View rootView;
        public IconFontTextview icf_all;
        public LinearLayout lin_all;
        public IconFontTextview icf_main;
        public LinearLayout lin_main;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.icf_all = (IconFontTextview) rootView.findViewById(R.id.icf_all);
            this.lin_all = (LinearLayout) rootView.findViewById(R.id.lin_all);
            this.icf_main = (IconFontTextview) rootView.findViewById(R.id.icf_main);
            this.lin_main = (LinearLayout) rootView.findViewById(R.id.lin_main);
        }

    }
}