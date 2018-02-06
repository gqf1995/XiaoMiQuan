package com.xiaomiquan.mvp.delegate.circle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CirclePreviewDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_preview;
    }


    public static class ViewHolder {
        public View rootView;

        public TextView tv_title;
        public TextView tv_creator;
        public TextView tv_num;
        public CircleImageView iv_head;
        public TextView tv_con;
        public RecyclerView ry_dynamic;
        public TextView tv_pay;
        public TextView tv_code;
        public LinearLayout lin_charge;
        public TextView tv_free;
        public LinearLayout lin_free;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_creator = (TextView) rootView.findViewById(R.id.tv_creator);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.iv_head = (CircleImageView) rootView.findViewById(R.id.iv_head);
            this.tv_con = (TextView) rootView.findViewById(R.id.tv_con);
            this.ry_dynamic = (RecyclerView) rootView.findViewById(R.id.ry_dynamic);
            this.tv_pay = (TextView) rootView.findViewById(R.id.tv_pay);
            this.tv_code = (TextView) rootView.findViewById(R.id.tv_code);
            this.lin_charge = (LinearLayout) rootView.findViewById(R.id.lin_charge);
            this.tv_free = (TextView) rootView.findViewById(R.id.tv_free);
            this.lin_free = (LinearLayout) rootView.findViewById(R.id.lin_free);
        }

    }
}