package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.GroupAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.group.GroupChangeBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

public class MyGocuseGroupFragment extends BasePullFragment<BaseFragentPullDelegate, GroupChangeBinder> {

    GroupAdapter groupAdapter;

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
        List<ExchangeData> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add(i, new ExchangeData());
        }
        groupAdapter = new GroupAdapter(getActivity(), datas);
        groupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, final int position, Object item) {
                if (view.getId() == R.id.tv_deal) {
                    gotoActivity(GroupDealActivity.class).startAct();
                }
                if (view.getId() == R.id.tv_look) {
                    gotoActivity(CombinationActivity.class).startAct();
                }
            }
        });
        initRecycleViewPull(groupAdapter, new LinearLayoutManager(getActivity()));
        onRefresh();
    }

    @Override
    protected void refreshData() {

    }
}
