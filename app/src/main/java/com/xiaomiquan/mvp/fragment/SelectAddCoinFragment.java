package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.adapter.AddCoinAdapter;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectAddCoinFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    String type;
    AddCoinAdapter addCoinAdapter;
    List<String> datas;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initList();
    }

    private void initList() {
        datas = new ArrayList<>();
        addCoinAdapter = new AddCoinAdapter(getActivity(), datas);
        addCoinAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                addCoinAdapter.select(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        initRecycleViewPull(addCoinAdapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsLoadMore(false);
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.show(type, this));
    }

    public static SelectAddCoinFragment newInstance(
            String type) {
        SelectAddCoinFragment newFragment = new SelectAddCoinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type")) {
            type = savedInstanceState.getString("type");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", type);
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }
}
