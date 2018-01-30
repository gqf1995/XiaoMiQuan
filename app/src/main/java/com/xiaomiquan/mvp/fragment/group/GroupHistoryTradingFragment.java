package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.adapter.group.LabelHistoryTradingAdapter;
import com.xiaomiquan.mvp.databinder.group.GroupChangeBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

public class GroupHistoryTradingFragment extends BasePullFragment<BaseFragentPullDelegate, GroupChangeBinder> {

    LabelHistoryTradingAdapter groupAdapter;

    @Override
    public GroupChangeBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new GroupChangeBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initList();
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    private void initList() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("");
        }
        groupAdapter = new LabelHistoryTradingAdapter(getActivity(), datas);
        initRecycleViewPull(groupAdapter, new LinearLayoutManager(getActivity()));
        onRefresh();
    }

    @Override
    protected void refreshData() {

    }
}
