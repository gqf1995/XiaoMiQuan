package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.JudgeNestedScrollView;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleShowDelegate extends BaseMyPullDelegate {

    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(rootView);
        viewHolder.judgeNested_scroll.setTabAndPager(viewHolder.lin_tab,
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_100px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_100px),
                viewHolder.pull_recycleview, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle_show;
    }

    public static class ViewHolder {
        public View rootView;
        public RecyclerView rv_mycircle;
        public AppCompatImageView civ_img;
        public RecyclerView pull_recycleview;
        public LinearLayout lin_root;
        public LinearLayout lin_tab;
        public JudgeNestedScrollView judgeNested_scroll;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.rv_mycircle = (RecyclerView) rootView.findViewById(R.id.rv_mycircle);
            this.civ_img = (AppCompatImageView) rootView.findViewById(R.id.civ_img);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.lin_root = (LinearLayout) rootView.findViewById(R.id.lin_root);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.judgeNested_scroll = (JudgeNestedScrollView) rootView.findViewById(R.id.judgeNested_scroll);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}
