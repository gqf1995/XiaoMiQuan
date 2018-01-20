package com.xiaomiquan.mvp.delegate;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class SortingUserCoinDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sorting_user_coin;
    }


    public static class ViewHolder {
        public View rootView;
        public SwipeMenuRecyclerView recycler_view;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.recycler_view = (SwipeMenuRecyclerView) rootView.findViewById(R.id.recycler_view);
        }

    }
}