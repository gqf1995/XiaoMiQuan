package com.xiaomiquan.mvp.fragment.circle;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.adapter.circle.CircleDynamicAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.CirclDynamicBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleDynamicFragment extends BasePullFragment<BaseFragentPullDelegate, CirclDynamicBinder> {
    List<UserCircle> userCircleList;
    CircleDynamicAdapter adapter;

    @Override
    public CirclDynamicBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new CirclDynamicBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }

    private void initList(List<UserCircle> strDatas) {
//        adapter = new CircleDynamicAdapter(getActivity(), userCircleList);
//

        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        onRefresh();
    }

    @Override
    protected void onFragmentFirstVisible() {
        userCircleList = new ArrayList<>();
        initList(userCircleList);
    }

    @Override
    protected void refreshData() {
//        addRequest(binder.getMoreCircle(this));
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }
}
