package com.xiaomiquan.mvp.delegate.circle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.base.BaseMyPullDelegate;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleContentDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_content;
    }

    public static class ViewHolder {
        public View rootView;
        public View v_status;
        public View view_subtitle_point;
        public IconFontTextview toolbar_img2;
        public View view_img2_point;
        public IconFontTextview toolbar_img1;
        public View view_img1_point;
        public IconFontTextview toolbar_img;
        public View view_img_point;
        public TextView toolbar_title;
        public LinearLayout layout_title_bar;
        public CircleImageView cv_head;
        public TextView tv_name;
        public TextView tv_creator;
        public TextView tv_num;
        public LinearLayout lin_my_circle;
        public IconFontTextview tv_all;
        public IconFontTextview tv_owner;
        public TextView tv_rank;
        public RecyclerView pull_recycleview;
        public SwipeRefreshLayout swipeRefreshLayout;
        public FloatingActionButton flt_send;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.view_subtitle_point = (View) rootView.findViewById(R.id.view_subtitle_point);
            this.toolbar_img2 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img2);
            this.view_img2_point = (View) rootView.findViewById(R.id.view_img2_point);
            this.toolbar_img1 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img1);
            this.view_img1_point = (View) rootView.findViewById(R.id.view_img1_point);
            this.toolbar_img = (IconFontTextview) rootView.findViewById(R.id.toolbar_img);
            this.view_img_point = (View) rootView.findViewById(R.id.view_img_point);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.layout_title_bar = (LinearLayout) rootView.findViewById(R.id.layout_title_bar);
            this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_creator = (TextView) rootView.findViewById(R.id.tv_creator);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.lin_my_circle = (LinearLayout) rootView.findViewById(R.id.lin_my_circle);
            this.tv_all = (IconFontTextview) rootView.findViewById(R.id.tv_all);
            this.tv_owner = (IconFontTextview) rootView.findViewById(R.id.tv_owner);
            this.tv_rank = (TextView) rootView.findViewById(R.id.tv_rank);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.flt_send = (FloatingActionButton) rootView.findViewById(R.id.flt_send);
        }

    }
}
