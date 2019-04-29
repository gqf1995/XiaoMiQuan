package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class CreatCodeDelegate extends BaseDelegate {
    public ViewHolder viewHolder;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_cirecle_code;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_code;
        public TextView tv_create;
        public TextView tv_copy;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_code = (TextView) rootView.findViewById(R.id.tv_code);
            this.tv_create = (TextView) rootView.findViewById(R.id.tv_create);
            this.tv_copy = (TextView) rootView.findViewById(R.id.tv_copy);
        }

    }
}