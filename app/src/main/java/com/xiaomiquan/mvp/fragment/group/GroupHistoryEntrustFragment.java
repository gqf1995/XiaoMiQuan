package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelHistoryEntrustAdapter;
import com.xiaomiquan.entity.bean.group.HistoryTrading;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史委托
 */
public class GroupHistoryEntrustFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    LabelHistoryEntrustAdapter adapter;


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
        id=getArguments().getString("id");
        initList(new ArrayList<HistoryTrading>());
    }


    private void initList(List<HistoryTrading> strDatas) {
        adapter = new LabelHistoryEntrustAdapter(getActivity(), strDatas);
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsPullDown(false);
        onRefresh();
        initTop();
    }

    private void initTop() {
        View rootView=getActivity().getLayoutInflater().inflate(R.layout.layout_label_history_entrust,null);
        viewDelegate.viewHolder.fl_pull.addView(rootView,0);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<HistoryTrading> data1 = GsonUtil.getInstance().toList(data, HistoryTrading.class);
                getDataBack(adapter.getDatas(), data1, adapter);
                break;
        }
    }



    @Override
    protected void refreshData() {
        addRequest(binder.history(id,this));
    }

    public static GroupHistoryEntrustFragment newInstance(
            String id
    ) {
        GroupHistoryEntrustFragment newFragment = new GroupHistoryEntrustFragment();
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

