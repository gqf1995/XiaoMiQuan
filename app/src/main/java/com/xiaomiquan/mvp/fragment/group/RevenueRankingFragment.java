package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelDetailDealAdapter;
import com.xiaomiquan.adapter.group.RevenueRankingAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.GroupRank;
import com.xiaomiquan.entity.bean.group.HoldDetail;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 收益排行
 */
public class RevenueRankingFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    RevenueRankingAdapter revenueRankingAdapter;

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();

    }

    @Override
    protected void onFragmentFirstVisible() {
        type = getArguments().getString("type");
        addRequest(binder.top(type, this));
    }

    private void initList(List<GroupItem> rankList) {
        revenueRankingAdapter = new RevenueRankingAdapter(getActivity(), rankList,type);
        initRecycleViewPull(revenueRankingAdapter, new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x124:
                List<GroupItem> data1 = GsonUtil.getInstance().toList(GsonUtil.getInstance().getValue(data, "tops"), GroupItem.class);
                initList(data1);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.top(type, this));
    }

    public static RevenueRankingFragment newInstance(
            String type
    ) {
        RevenueRankingFragment newFragment = new RevenueRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    String type;

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

