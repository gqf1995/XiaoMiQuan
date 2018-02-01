package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelHistoryTradingAdapter;
import com.xiaomiquan.mvp.databinder.group.GroupChangeBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史交易
 */
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
        id=getArguments().getString("id");
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
        initTop();
    }

    private void initTop() {
        View rootView=getActivity().getLayoutInflater().inflate(R.layout.layout_label_history_trading,null);
        viewDelegate.viewHolder.fl_pull.addView(rootView,0);
    }
    @Override
    protected void refreshData() {

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
