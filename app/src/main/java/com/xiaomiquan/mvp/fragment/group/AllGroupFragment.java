package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.adapter.group.AllMyGroupAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.AllGroupDelegate;

import java.util.List;

/**
 * 全部组合
 */
public class AllGroupFragment extends BasePullFragment<AllGroupDelegate, BaseFragmentPullBinder> {
    AllMyGroupAdapter allMyGroupAdapter;

    @Override
    protected Class<AllGroupDelegate> getDelegateClass() {
        return AllGroupDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(AllGroupDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        addRequest(binder.listDemo(this));
    }

    private void initAllMyGroup(List<GroupItem> datas) {
        allMyGroupAdapter = new AllMyGroupAdapter(getActivity(), datas);
        allMyGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                GroupDealActivity.startAct(getActivity(), allMyGroupAdapter.getDatas().get(position), true);
            }
        });
        viewDelegate.viewHolder.rv_my_group.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_my_group.setAdapter(allMyGroupAdapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<GroupItem> datas = GsonUtil.getInstance().toList(data, GroupItem.class);
                initAllMyGroup(datas);
                break;
        }
    }

    @Override
    protected void refreshData() {

    }
}
