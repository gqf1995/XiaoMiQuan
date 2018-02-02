package com.xiaomiquan.mvp.delegate.circle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.xiaomiquan.R;

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
        public RecyclerView ry_entrance;
        public RecyclerView ry_message;
        public ImageView iv_img;
        public TextView tv_more;
        public RecyclerView ry_live;
        public FloatingActionButton flt_send;
        public SwipeRefreshLayout swipeRefreshLayout;
        public BGABanner banner_img;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ry_entrance = (RecyclerView) rootView.findViewById(R.id.ry_entrance);
            this.ry_message = (RecyclerView) rootView.findViewById(R.id.ry_message);
            this.iv_img = (ImageView) rootView.findViewById(R.id.iv_img);
            this.tv_more = (TextView) rootView.findViewById(R.id.tv_more);
            this.ry_live = (RecyclerView) rootView.findViewById(R.id.ry_live);
            this.flt_send = (FloatingActionButton) rootView.findViewById(R.id.flt_send);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.banner_img = (BGABanner) rootView.findViewById(R.id.banner_img);
        }

    }
}
