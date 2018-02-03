package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleShowDelegate extends BaseMyPullDelegate {

    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(rootView);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle_show;
    }

    public static class ViewHolder {
        public View rootView;
        public RecyclerView rv_mycircle;
        public RecyclerView rv_circle;
        public CommonTabLayout tl_2;
        public ViewPager vp_sliding;
        public LinearLayout lin_root;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.rv_mycircle = (RecyclerView) rootView.findViewById(R.id.rv_mycircle);
            this.rv_circle = (RecyclerView) rootView.findViewById(R.id.rv_circle);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
            this.lin_root = (LinearLayout) rootView.findViewById(R.id.lin_root);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}
