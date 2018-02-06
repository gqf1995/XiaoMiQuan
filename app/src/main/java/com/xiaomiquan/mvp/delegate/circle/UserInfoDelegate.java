package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView cv_head;
        public TextView tv_name;
        public TextView tv_attention;
        public TextView tv_fans;
        public TextView tv_input_label1;
        public ImageView iv_circle;
        public TextView tv_circle_name;
        public TextView tv_attention_num;
        public TextView tv_join_circle;
        public TextView tv_circle_brief;
        public LinearLayout lin_attention;
        public LinearLayout lin_fans;
        public LinearLayout lin_my_circle;
        public RecyclerView ry_live;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_attention = (TextView) rootView.findViewById(R.id.tv_attention);
            this.tv_fans = (TextView) rootView.findViewById(R.id.tv_fans);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.iv_circle = (ImageView) rootView.findViewById(R.id.iv_circle);
            this.tv_circle_name = (TextView) rootView.findViewById(R.id.tv_circle_name);
            this.tv_attention_num = (TextView) rootView.findViewById(R.id.tv_attention_num);
            this.tv_join_circle = (TextView) rootView.findViewById(R.id.tv_join_circle);
            this.tv_circle_brief = (TextView) rootView.findViewById(R.id.tv_circle_brief);
            this.lin_my_circle = (LinearLayout) rootView.findViewById(R.id.lin_my_circle);
            this.lin_attention = (LinearLayout) rootView.findViewById(R.id.lin_attention);
            this.lin_fans = (LinearLayout) rootView.findViewById(R.id.lin_fans);
            this.ry_live = (RecyclerView) rootView.findViewById(R.id.ry_live);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}