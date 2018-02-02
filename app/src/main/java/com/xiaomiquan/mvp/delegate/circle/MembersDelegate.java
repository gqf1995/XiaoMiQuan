package com.xiaomiquan.mvp.delegate.circle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_members;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_input_label1;
        public CircleImageView cv_head;
        public TextView tv_name;
        public TextView tv_time;
        public RecyclerView rv_person;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.rv_person = (RecyclerView) rootView.findViewById(R.id.rv_person);
        }

    }
}