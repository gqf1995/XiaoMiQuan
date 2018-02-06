package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.adapter.group.AllMyGroupAdapter;
import com.xiaomiquan.adapter.group.HotGroupAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.GroupRank;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.AllGroupDelegate;

import java.util.List;

/**
 * 全部组合
 */
public class AllGroupFragment extends BasePullFragment<AllGroupDelegate, BaseFragmentPullBinder> {
    AllMyGroupAdapter allMyGroupAdapter;
    HotGroupAdapter hotGroupAdapter;
    String[] types = {"1", "2", "3"};
    int index = -1;
    GroupRank list7;
    GroupRank list30;
    GroupRank listall;

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
//        addRequest(binder.top(types[index++], this));
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

    private void initHotList(GroupRank groupRank) {
        if (hotGroupAdapter == null) {
            hotGroupAdapter = new HotGroupAdapter(getActivity(), groupRank.getTops());
            hotGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {

                }
            });
            viewDelegate.viewHolder.rcv_hot_group.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rcv_hot_group.setAdapter(hotGroupAdapter);
            viewDelegate.initRank(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    if (position == 0) {
                        initHotList(list7);
                    } else if (position == 1) {
                        initHotList(list30);
                    } else {
                        initHotList(listall);
                    }
                }

                @Override
                public void onTabReselect(int position) {

                }
            });
        } else {
            hotGroupAdapter.setDatas(groupRank.getTops());
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<GroupItem> datas = GsonUtil.getInstance().toList(data, GroupItem.class);
                initAllMyGroup(datas);
                break;
            case 0x124:
                if (index == 0) {
                    list7 = GsonUtil.getInstance().toObj(data, GroupRank.class);
                } else if (index == 1) {
                    list30 = GsonUtil.getInstance().toObj(data, GroupRank.class);
                } else if (index == 2) {
                    listall = GsonUtil.getInstance().toObj(data, GroupRank.class);
                    initHotList(list7);
                }
                if (index < 3) {
                    addRequest(binder.top(types[index++], this));
                }
                break;
        }
    }

    @Override
    protected void refreshData() {

    }
}
