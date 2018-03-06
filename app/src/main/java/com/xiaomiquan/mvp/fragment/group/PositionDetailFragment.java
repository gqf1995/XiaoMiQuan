package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.adapter.group.PositionDetailAdapter;
import com.xiaomiquan.mvp.databinder.group.PositionDetailBinder;
import com.xiaomiquan.mvp.delegate.group.PositionDetailDelegate;

import java.util.ArrayList;
import java.util.List;

public class PositionDetailFragment extends BaseDataBindFragment<PositionDetailDelegate, PositionDetailBinder> {

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
        initList();
    }

    private void initList() {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            str.add("" + i);
        }
        PositionDetailAdapter positionDetailAdapter = new PositionDetailAdapter(getActivity(), str);
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewDelegate.viewHolder.pull_recycleview.setAdapter(positionDetailAdapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
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
