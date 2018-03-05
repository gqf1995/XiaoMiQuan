package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.base.BasePullDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatToolbar;

public class GetFriendsJoinDelegate extends BasePullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public View v_status;
        public IconFontTextview toolbar_back;
        public View view_back_point;
        public TextView toolbar_back_txt;
        public LinearLayout toolbar_lin_back;
        public FrameLayout fl_content;
        public IconFontTextview toolbar_subtitle;
        public View view_subtitle_point;
        public IconFontTextview toolbar_img2;
        public View view_img2_point;
        public IconFontTextview toolbar_img1;
        public View view_img1_point;
        public IconFontTextview toolbar_img;
        public View view_img_point;
        public TextView toolbar_title;
        public SkinCompatToolbar toolbar;
        public LinearLayout layout_title_bar;
        public RecyclerView pull_recycleview;
        public FrameLayout fl_rcv;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.toolbar_back = (IconFontTextview) rootView.findViewById(R.id.toolbar_back);
            this.view_back_point = (View) rootView.findViewById(R.id.view_back_point);
            this.toolbar_back_txt = (TextView) rootView.findViewById(R.id.toolbar_back_txt);
            this.toolbar_lin_back = (LinearLayout) rootView.findViewById(R.id.toolbar_lin_back);
            this.fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
            this.toolbar_subtitle = (IconFontTextview) rootView.findViewById(R.id.toolbar_subtitle);
            this.view_subtitle_point = (View) rootView.findViewById(R.id.view_subtitle_point);
            this.toolbar_img2 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img2);
            this.view_img2_point = (View) rootView.findViewById(R.id.view_img2_point);
            this.toolbar_img1 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img1);
            this.view_img1_point = (View) rootView.findViewById(R.id.view_img1_point);
            this.toolbar_img = (IconFontTextview) rootView.findViewById(R.id.toolbar_img);
            this.view_img_point = (View) rootView.findViewById(R.id.view_img_point);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (SkinCompatToolbar) rootView.findViewById(R.id.toolbar);
            this.layout_title_bar = (LinearLayout) rootView.findViewById(R.id.layout_title_bar);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.fl_rcv = (FrameLayout) rootView.findViewById(R.id.fl_rcv);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}