package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.PositionDetailAdapter;
import com.xiaomiquan.entity.bean.group.HoldDetail;
import com.xiaomiquan.mvp.databinder.group.PositionDetailBinder;
import com.xiaomiquan.mvp.delegate.group.PositionDetailDelegate;

import java.util.List;

public class PositionDetailFragment extends BaseDataBindFragment<PositionDetailDelegate, PositionDetailBinder> {

    PositionDetailAdapter positionDetailAdapter;
    GroupDetailListFragment groupDetailListFragment;

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
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag("GroupDetailListFragment") == null) {
            groupDetailListFragment = GroupDetailListFragment.newInstance("0");
            transaction.add(R.id.fl_content, groupDetailListFragment, "GroupDetailListFragment");
        } else {
            groupDetailListFragment = (GroupDetailListFragment) getChildFragmentManager().findFragmentByTag("GroupDetailListFragment");
            transaction.show(groupDetailListFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onFragmentFirstVisible() {

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<HoldDetail> data1 = GsonUtil.getInstance().toList(data, HoldDetail.class);

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
