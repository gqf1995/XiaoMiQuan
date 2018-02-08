package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.StickyScrollView;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareDelegate extends BaseMyPullDelegate {

    public ViewHolder viewHolder;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }


    public static class ViewHolder {
        public View rootView;

        public BGABanner banner_img;
        public RecyclerView ry_entrance;
        public AppCompatImageView civ_img;
        public TextView tv_message_time;
        public LinearLayout lin_news;
        public RecyclerView ry_message;
        public ImageView iv_img;
        public TextView tv_live_time;
        public LinearLayout lin_live;
        public RecyclerView pull_recycleview;
        public StickyScrollView scrollView_scroll;
        public SwipeRefreshLayout swipeRefreshLayout;
        public AppCompatImageView civ_send;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.banner_img = (BGABanner) rootView.findViewById(R.id.banner_img);
            this.ry_entrance = (RecyclerView) rootView.findViewById(R.id.ry_entrance);
            this.civ_img = (AppCompatImageView) rootView.findViewById(R.id.civ_img);
            this.tv_message_time = (TextView) rootView.findViewById(R.id.tv_message_time);
            this.lin_news = (LinearLayout) rootView.findViewById(R.id.lin_news);
            this.ry_message = (RecyclerView) rootView.findViewById(R.id.ry_message);
            this.iv_img = (ImageView) rootView.findViewById(R.id.iv_img);
            this.tv_live_time = (TextView) rootView.findViewById(R.id.tv_live_time);
            this.lin_live = (LinearLayout) rootView.findViewById(R.id.lin_live);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.scrollView_scroll = (StickyScrollView) rootView.findViewById(R.id.scrollView_scroll);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.civ_send = (AppCompatImageView) rootView.findViewById(R.id.civ_send);
        }

    }
}
