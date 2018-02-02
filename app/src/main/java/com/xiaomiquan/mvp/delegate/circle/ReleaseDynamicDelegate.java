package com.xiaomiquan.mvp.delegate.circle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatCheckBox;

public class ReleaseDynamicDelegate extends BaseDelegate {

    private String circle_creat_name = "";
    private String circle_creat_brief = "";

    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_dynamic;
    }


    public static class ViewHolder {
        public View rootView;
        public MaterialEditText et_con;
        public TextView tv_scale;
        public RecyclerView rv_img;
        public SkinCompatCheckBox ck_live;
        public SkinCompatCheckBox ck_circle;
        public IconFontTextview icf_img;
        public IconFontTextview icf_font;
        public IconFontTextview icf_link;
        public IconFontTextview icf_into;
        public IconFontTextview icf_plus;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_con = (MaterialEditText) rootView.findViewById(R.id.et_con);
            this.tv_scale = (TextView) rootView.findViewById(R.id.tv_scale);
            this.rv_img = (RecyclerView) rootView.findViewById(R.id.rv_img);
            this.ck_live = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_live);
            this.ck_circle = (SkinCompatCheckBox) rootView.findViewById(R.id.ck_circle);
            this.icf_img = (IconFontTextview) rootView.findViewById(R.id.icf_img);
            this.icf_font = (IconFontTextview) rootView.findViewById(R.id.icf_font);
            this.icf_link = (IconFontTextview) rootView.findViewById(R.id.icf_link);
            this.icf_into = (IconFontTextview) rootView.findViewById(R.id.icf_into);
            this.icf_plus = (IconFontTextview) rootView.findViewById(R.id.icf_plus);
        }

    }
}