package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

public class EditCircleDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_redact_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_head;
        public MaterialEditText tv_title;
        public TextView tv_creator;
        public TextView tv_input_label1;
        public TextView tv_people_num;
        public MaterialEditText tv_con;
        public TextView tv_shared;
        public LinearLayout lin_set1;
        public LinearLayout lin_set2;
        public LinearLayout lin_set3;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_head = (ImageView) rootView.findViewById(R.id.iv_head);
            this.tv_title = (MaterialEditText) rootView.findViewById(R.id.tv_title);
            this.tv_creator = (TextView) rootView.findViewById(R.id.tv_creator);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.tv_people_num = (TextView) rootView.findViewById(R.id.tv_people_num);
            this.tv_con = (MaterialEditText) rootView.findViewById(R.id.tv_con);
            this.tv_shared = (TextView) rootView.findViewById(R.id.tv_shared);
            this.lin_set1 = (LinearLayout) rootView.findViewById(R.id.lin_set1);
            this.lin_set2 = (LinearLayout) rootView.findViewById(R.id.lin_set2);
            this.lin_set3 = (LinearLayout) rootView.findViewById(R.id.lin_set3);
        }

    }
}
