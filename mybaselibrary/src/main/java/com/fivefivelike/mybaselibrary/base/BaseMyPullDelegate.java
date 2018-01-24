package com.fivefivelike.mybaselibrary.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.view.LoadMoreListView;


/**
 * Created by 郭青枫 on 2017/9/26.
 */

public abstract class BaseMyPullDelegate extends BasePullDelegate {

    //设置 下拉刷新ui颜色
    public void initRecycleviewPull(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager, LoadMoreListView.Callback callback, int headerCount, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        super.initRecycleviewPull(adapter, manager, callback, headerCount, onRefreshListener);
        setColorSchemeResources(R.color.black);
    }
}