package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelDetailDealAdapter;
import com.xiaomiquan.adapter.group.RevenueRankingAdapter;
import com.xiaomiquan.entity.bean.group.HoldDetail;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 收益排行
 */
public class RevenueRankingFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

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
        type = getArguments().getString("type");
        ToastUtil.show(type);
        initList();
    }


    private void initList() {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            str.add(i + "");
        }
        RevenueRankingAdapter revenueRankingAdapter = new RevenueRankingAdapter(getActivity(), str);
        initRecycleViewPull(revenueRankingAdapter, new LinearLayoutManager(getActivity()));
    }

    private void initTop() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_label_detail, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<HoldDetail> data1 = GsonUtil.getInstance().toList(data, HoldDetail.class);
//                getDataBack(adapter.getDatas(), data1, adapter);
                break;
        }
    }


    @Override
    protected void refreshData() {
        addRequest(binder.listPosition(type, this));
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

