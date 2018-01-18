package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.adapter.ExchangeAdapter;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExchangeNameListFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    ArrayList<ExchangeName> exchangeNames;
    ExchangeAdapter adapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initList();
    }

    private DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    private void initList() {
        exchangeNames = getArguments().getParcelableArrayList("exchangeNames");
        adapter = new ExchangeAdapter(getActivity(), exchangeNames);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                defaultClickLinsener.onClick(view, position, adapter.getDatas().get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsLoadMore(false);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getAllEXchange(this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<ExchangeName> datas = GsonUtil.getInstance().toList(data, ExchangeName.class);
                getDataBack(exchangeNames, datas, adapter);
                break;
        }
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }

    public static ExchangeNameListFragment newInstance(
            ArrayList<ExchangeName> exchangeNames) {
        ExchangeNameListFragment newFragment = new ExchangeNameListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("exchangeNames", exchangeNames);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("exchangeNames")) {
            exchangeNames = savedInstanceState.getParcelable("exchangeNames");
        }
    }

}
