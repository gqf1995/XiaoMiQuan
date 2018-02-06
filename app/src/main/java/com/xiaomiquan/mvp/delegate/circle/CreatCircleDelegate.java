package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatCheckBox;

public class CreatCircleDelegate extends BaseDelegate {


    public ViewHolder viewHolder;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.ck_free.setTextColor(CommonUtils.getColor(R.color.color_font1));
        viewHolder.ck_charge.setTextColor(CommonUtils.getColor(R.color.color_font1));
        viewHolder.ck_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.ck_free.isChecked()) {
                    viewHolder.ck_charge.setChecked(false);
                }
            }
        });
        viewHolder.ck_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.ck_charge.isChecked()) {
                    viewHolder.ck_free.setChecked(false);
                }
            }
        });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public SkinCompatCheckBox ck_free;
        public SkinCompatCheckBox ck_charge;
        public TextView tv_next;
        public LinearLayout lin_choose;
        public IconFontTextview icv_change_img;
        public LinearLayout lin_next;
        public MaterialEditText et_name;
        public MaterialEditText et_brief;
        public TextView tv_input_label1;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ck_free = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_free);
            this.ck_charge = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_charge);
            this.tv_next = (TextView) rootView.findViewById(R.id.tv_next);
            this.lin_choose = (LinearLayout) rootView.findViewById(R.id.lin_choose);
            this.icv_change_img = (IconFontTextview) rootView.findViewById(R.id.icv_change_img);
            this.lin_next = (LinearLayout) rootView.findViewById(R.id.lin_next);
            this.et_name = (MaterialEditText) rootView.findViewById(R.id.et_name);
            this.et_brief = (MaterialEditText) rootView.findViewById(R.id.et_brief);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
        }

    }
}