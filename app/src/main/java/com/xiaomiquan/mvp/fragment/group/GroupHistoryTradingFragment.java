package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelHistoryTradingAdapter;
import com.xiaomiquan.entity.bean.group.HistoryTrading;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史交易
 */
public class GroupHistoryTradingFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    LabelHistoryTradingAdapter groupAdapter;

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<HistoryTrading> datas = GsonUtil.getInstance().toList(data, HistoryTrading.class);
                getDataBack(groupAdapter.getDatas(), datas, groupAdapter);
                break;
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        id = getArguments().getString("id");
        initList();
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    private void initList() {
        groupAdapter = new LabelHistoryTradingAdapter(getActivity(), new ArrayList<HistoryTrading>());
        initRecycleViewPull(groupAdapter, new LinearLayoutManager(getActivity()));
        onRefresh();
        initTop();
    }

    private void initTop() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_label_history_trading, null);
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.listDeal(id, "2", this));
    }

    public static GroupHistoryTradingFragment newInstance(
            String id
    ) {
        GroupHistoryTradingFragment newFragment = new GroupHistoryTradingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("id")) {
            id = savedInstanceState.getString("id");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", id);
    }
}
