package com.xiaomiquan.mvp.delegate.group;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatCheckBox;

public class CreatGroupDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.ck_circle.setTextColor(CommonUtils.getColor(R.color.color_font1));
        viewHolder.ck_live.setTextColor(CommonUtils.getColor(R.color.color_font1));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_group;
    }


    public static class ViewHolder {
        public View rootView;
        public EditText et_con;
        public SkinCompatCheckBox ck_live;
        public SkinCompatCheckBox ck_circle;
        public TextView tv_input_label1;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_con = (EditText) rootView.findViewById(R.id.et_con);
            this.ck_live = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_live);
            this.ck_circle = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_circle);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
        }

    }
}