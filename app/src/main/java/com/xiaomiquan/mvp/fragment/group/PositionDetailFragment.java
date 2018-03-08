package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.group.PositionDetailAdapter;
import com.xiaomiquan.entity.bean.group.HoldDetail;
import com.xiaomiquan.mvp.databinder.group.PositionDetailBinder;
import com.xiaomiquan.mvp.delegate.group.PositionDetailDelegate;

import java.util.ArrayList;
import java.util.List;

public class PositionDetailFragment extends BaseDataBindFragment<PositionDetailDelegate, PositionDetailBinder> {

    PositionDetailAdapter positionDetailAdapter;

    @Override
    protected Class<PositionDetailDelegate> getDelegateClass() {
        return PositionDetailDelegate.class;
    }

    @Override
    public PositionDetailBinder getDataBinder(PositionDetailDelegate viewDelegate) {
        return new PositionDetailBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }

    @Override
    protected void onFragmentFirstVisible() {
        initList(new ArrayList<HoldDetail>());
    }

    private void initList(List<HoldDetail> holdDetails) {
        if (positionDetailAdapter == null) {
            addRequest(binder.listPosition(id, this));
            positionDetailAdapter = new PositionDetailAdapter(getActivity(), holdDetails);
            viewDelegate.viewHolder.pull_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
            viewDelegate.viewHolder.pull_recycleview.setAdapter(positionDetailAdapter);
        } else {
            positionDetailAdapter.setDatas(holdDetails);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<HoldDetail> data1 = GsonUtil.getInstance().toList(data, HoldDetail.class);
                initList(data1);
                break;
        }
    }

    public static PositionDetailFragment newInstance(
            String id
    ) {
        PositionDetailFragment newFragment = new PositionDetailFragment();
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
