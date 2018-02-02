package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatCheckBox;
import skin.support.widget.SkinCompatRadioButton;
import skin.support.widget.SkinCompatRadioGroup;

public class CreatCircleDelegate extends BaseDelegate {

    private String circle_creat_name = "";
    private String circle_creat_brief = "";

    public ViewHolder viewHolder;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();

    }

    public void init() {
        viewHolder.tv_next.setOnClickListener(onClickListener);
        initCrate();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_next:

                    break;
            }
        }
    };

    private void saveInput() {
        circle_creat_name = viewHolder.et_name.getText().toString();
        circle_creat_brief = viewHolder.et_brief.getText().toString();
    }

    public void initCrate() {
        saveInput();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public SkinCompatRadioButton rb_free;
        public SkinCompatRadioButton rb_charge;
        public SkinCompatRadioGroup rg_choose;
        public LinearLayout lin_free;
        public LinearLayout lin_choose;
        public LinearLayout lin_next;
        public LinearLayout lin_charge;
        public SkinCompatCheckBox ck_agree;
        public TextView tv_rule;
        public TextView tv_next;
        public IconFontTextview icv_change_img;
        public MaterialEditText et_name;
        public MaterialEditText et_brief;
        public TextView tv_input_label1;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.rb_free = (SkinCompatRadioButton) rootView.findViewById(R.id.rb_free);
            this.rb_charge = (SkinCompatRadioButton) rootView.findViewById(R.id.rb_charge);
            this.rg_choose = (SkinCompatRadioGroup) rootView.findViewById(R.id.rg_choose);
            this.lin_free = (LinearLayout) rootView.findViewById(R.id.lin_free);
            this.lin_choose = (LinearLayout) rootView.findViewById(R.id.lin_choose);
            this.lin_next = (LinearLayout) rootView.findViewById(R.id.lin_next);
            this.lin_charge = (LinearLayout) rootView.findViewById(R.id.lin_charge);
            this.ck_agree = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_agree);
            this.tv_rule = (TextView) rootView.findViewById(R.id.tv_rule);
            this.tv_next = (TextView) rootView.findViewById(R.id.tv_next);
            this.icv_change_img = (IconFontTextview) rootView.findViewById(R.id.icv_change_img);
            this.et_name = (MaterialEditText) rootView.findViewById(R.id.et_name);
            this.et_brief = (MaterialEditText) rootView.findViewById(R.id.et_brief);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
        }

    }
}