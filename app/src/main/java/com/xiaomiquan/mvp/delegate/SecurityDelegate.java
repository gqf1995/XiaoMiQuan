package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class SecurityDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_phone_num;
        public LinearLayout lin_phone;
        public TextView tv_email;
        public LinearLayout lin_email;
        public LinearLayout lin_change_password;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_phone_num = (TextView) rootView.findViewById(R.id.tv_phone_num);
            this.lin_phone = (LinearLayout) rootView.findViewById(R.id.lin_phone);
            this.tv_email = (TextView) rootView.findViewById(R.id.tv_email);
            this.lin_email = (LinearLayout) rootView.findViewById(R.id.lin_email);
            this.lin_change_password = (LinearLayout) rootView.findViewById(R.id.lin_change_password);
        }
    }

}