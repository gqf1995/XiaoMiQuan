package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelNotDealAdapter;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 未成交
 */
public class GroupNotDealFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    List<String> strDatas;
    LabelNotDealAdapter adapter;


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


    private void initList(List<String> strDatas) {
        for (int i = 0; i < 20; i++) {
            strDatas.add("");
        }
        adapter = new LabelNotDealAdapter(getActivity(), strDatas);
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsPullDown(false);
        onRefresh();
        initTop();
    }

    private void initTop() {
        View rootView=getActivity().getLayoutInflater().inflate(R.layout.layout_label_not_deal,null);
        viewDelegate.viewHolder.fl_pull.addView(rootView,0);
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
        id=getArguments().getString("id");
        strDatas = new ArrayList<>();
        initList(strDatas);
    }

    @Override
    protected void refreshData() {
        //addRequest(binder.listArticleByPage(this));
    }

    public static GroupNotDealFragment newInstance(
            String id
    ) {
        GroupNotDealFragment newFragment = new GroupNotDealFragment();
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

