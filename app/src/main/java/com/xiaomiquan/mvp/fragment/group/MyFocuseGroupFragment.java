package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.FocuseGroupAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.databinder.group.GroupChangeBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注组合
 */
public class MyFocuseGroupFragment extends BasePullFragment<BaseFragentPullDelegate, GroupChangeBinder> {

    FocuseGroupAdapter myGroupAdapter;

    @Override
    public GroupChangeBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new GroupChangeBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data,info,status,requestCode);
        switch (requestCode) {
            case 0x123:
                List<GroupItem> datas = GsonUtil.getInstance().toList(data, GroupItem.class);
                initList(datas);
                break;
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initList(new ArrayList<GroupItem>());
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    public void notifyDataSetChanged() {
        if (myGroupAdapter != null) {
            myGroupAdapter.notifyDataSetChanged();
        }
    }

    private void initList(List<GroupItem> datas) {
        if (myGroupAdapter == null) {
            myGroupAdapter = new FocuseGroupAdapter(getActivity(), datas);
            myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, final int position, Object item) {
                    if (view.getId() == R.id.tv_deal) {
                        CombinationActivity.startAct(getActivity(), myGroupAdapter.getDatas().get(position), false);
                    }
                    if (view.getId() == R.id.tv_look) {
                        CombinationActivity.startAct(getActivity(), myGroupAdapter.getDatas().get(position), false);
                    }
                }
            });
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            initRecycleViewPull(myGroupAdapter, new LinearLayoutManager(getActivity()));
        } else {
            getDataBack(myGroupAdapter.getDatas(), datas, myGroupAdapter);
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
    protected void refreshData() {
        addRequest(binder.listAttentionDemo(this));
    }
}
