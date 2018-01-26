package com.xiaomiquan.mvp.fragment.circle;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.adapter.circle.CircleDynamicAdapter;
import com.xiaomiquan.adapter.circle.CircleFindAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.CirclDynamicBinder;
import com.xiaomiquan.mvp.databinder.circle.CirclFindBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.mvp.delegate.circle.CircleDynamicDelegate;
import com.xiaomiquan.mvp.delegate.circle.CircleFindDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleFindFragment extends BasePullFragment<BaseFragentPullDelegate, CirclFindBinder> {

    List<UserCircle> userCircleList;
    CircleFindAdapter circleFindAdapter;

    @Override
    public CirclFindBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new CirclFindBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
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
        circleFindAdapter = new CircleFindAdapter(getActivity(), userCircleList);
        initRecycleViewPull(circleFindAdapter, new LinearLayoutManager(getActivity()));
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
}
