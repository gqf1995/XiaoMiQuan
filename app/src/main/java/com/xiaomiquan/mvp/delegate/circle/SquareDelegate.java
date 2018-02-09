package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import skin.support.widget.SkinCompatLinearLayout;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareDelegate extends BaseMyPullDelegate {

    public ViewHolder viewHolder;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.scrollView_scroll.setTabAndPager(viewHolder.lin_tab, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_100px), (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_80px), viewHolder.pull_recycleview, false);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }


    public static class ViewHolder {
        public View rootView;
        public RecyclerView ry_entrance;
        public TextView tv_live_time;
        public LinearLayout lin_live;
        public LinearLayout lin_tab;
        public RecyclerView pull_recycleview;
        public JudgeNestedScrollView scrollView_scroll;
        public AppCompatImageView civ_send;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ry_entrance = (RecyclerView) rootView.findViewById(R.id.ry_entrance);
            this.tv_live_time = (TextView) rootView.findViewById(R.id.tv_live_time);
            this.lin_live = (LinearLayout) rootView.findViewById(R.id.lin_live);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.scrollView_scroll = (JudgeNestedScrollView) rootView.findViewById(R.id.scrollView_scroll);
            this.civ_send = (AppCompatImageView) rootView.findViewById(R.id.civ_send);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}
