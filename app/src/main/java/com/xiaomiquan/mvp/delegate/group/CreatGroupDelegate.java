package com.xiaomiquan.mvp.delegate.group;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatCheckBox;

public class CreatGroupDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.ck_circle.setTextColor(CommonUtils.getColor(R.color.color_font1));
        viewHolder.ck_live.setTextColor(CommonUtils.getColor(R.color.color_font1));
        viewHolder.ck_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.ck_circle.isChecked()) {
                    viewHolder.ck_live.setChecked(false);
                }
            }
        });
        viewHolder.ck_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.ck_live.isChecked()) {
                    viewHolder.ck_circle.setChecked(false);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_group;
    }

    public void initCreateTeam() {
        viewHolder.tv_title.setText(CommonUtils.getString(R.string.str_tv_team_name));
        viewHolder.tv_content.setText(CommonUtils.getString(R.string.str_tv_team_info));
        viewHolder.lin_select.setVisibility(View.GONE);
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_title;
        public EditText et_con;
        public TextView tv_content;
        public EditText et_content;
        public SkinCompatCheckBox ck_live;
        public SkinCompatCheckBox ck_circle;
        public LinearLayout lin_select;
        public SwitchButton checkbox_synchronous;
        public TextView tv_input_label1;
        public TextView tv_notice1;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.et_con = (EditText) rootView.findViewById(R.id.et_con);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.et_content = (EditText) rootView.findViewById(R.id.et_content);
            this.ck_live = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_live);
            this.ck_circle = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_circle);
            this.lin_select = (LinearLayout) rootView.findViewById(R.id.lin_select);
            this.checkbox_synchronous = (SwitchButton) rootView.findViewById(R.id.checkbox_synchronous);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.tv_notice1 = (TextView) rootView.findViewById(R.id.tv_notice1);
        }

    }
}