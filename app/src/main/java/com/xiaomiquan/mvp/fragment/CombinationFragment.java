package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.LiveAdapter;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.LoadMorePullDelegate;

import java.util.ArrayList;
import java.util.List;

public class CombinationFragment extends BasePullFragment<LoadMorePullDelegate, BaseFragmentPullBinder> {
    List<LiveData> strDatas;
    LiveAdapter adapter;
    String type;


    @Override
    protected Class<LoadMorePullDelegate> getDelegateClass() {
        return LoadMorePullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(LoadMorePullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    private void initList(List<LiveData> strDatas) {
        adapter = new LiveAdapter(getActivity(), strDatas);
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.setIsPullDown(false);
        onRefresh();
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<LiveData> data1 = GsonUtil.getInstance().toList(data, LiveData.class);
                getDataBack(strDatas, data1, adapter);
                break;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        strDatas = new ArrayList<>();
        initList(strDatas);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.listArticleByPage(this));
    }

    public static CombinationFragment newInstance(
            String type) {
        CombinationFragment newFragment = new CombinationFragment();
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

}

