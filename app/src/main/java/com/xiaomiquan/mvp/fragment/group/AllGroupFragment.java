package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.AllMyGroupAdapter;
import com.xiaomiquan.adapter.group.GroupDynamicAdapter;
import com.xiaomiquan.adapter.group.HotGroupAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.group.GroupDynamic;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.GroupRank;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.AllGroupDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部组合
 */
public class AllGroupFragment extends BasePullFragment<AllGroupDelegate, BaseFragmentPullBinder> {
    AllMyGroupAdapter allMyGroupAdapter;
    HotGroupAdapter hotGroupAdapter;
    String[] types = {"1", "2", "3"};
    int index = 0;
    GroupRank list7;
    GroupRank list30;
    GroupRank listall;
    UserLogin userLogin;
    GroupDynamicAdapter adapter;


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
        initList(new ArrayList<GroupDynamic>());
        addRequest(binder.top(types[index++], this));
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        userLogin = SingSettingDBUtil.getUserLogin();
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }

    public void notifyDataSetChanged() {
        if (allMyGroupAdapter != null && hotGroupAdapter != null && adapter != null) {
            allMyGroupAdapter.notifyDataSetChanged();
            hotGroupAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }

    private void initAllMyGroup(List<GroupItem> datas) {
        allMyGroupAdapter = new AllMyGroupAdapter(getActivity(), datas);
        allMyGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                GroupDealActivity.startAct(getActivity(), allMyGroupAdapter.getDatas(), position, true);
            }
        });
        viewDelegate.viewHolder.rv_my_group.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_my_group.setAdapter(allMyGroupAdapter);
        viewDelegate.viewHolder.lin_my_group.setVisibility(View.VISIBLE);
    }

    private void initHotList(GroupRank data) {
        GroupRank groupRank = new GroupRank(data.getCode(), data.getTopName(), data.getTops());
        if (hotGroupAdapter == null) {
            hotGroupAdapter = new HotGroupAdapter(getActivity(), groupRank.getTops());
            hotGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (hotGroupAdapter.getDatas().get(position).getIsAttention() == 0) {
                        //查看详情
                        CombinationActivity.startAct(getActivity(), hotGroupAdapter.getDatas().get(position), false);
                    } else if (hotGroupAdapter.getDatas().get(position).getIsAttention() == 1) {
                        if (userLogin != null) {
                            hotGroupAdapter.getDatas().get(position).setAttentionCount(0 + "");
                            //关注
                            addRequest(binder.demoattention(hotGroupAdapter.getDatas().get(position).getUserId() + "", null));
                            hotGroupAdapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                        }
                    }
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

    private void initList(List<GroupDynamic> datas) {
        if (adapter == null) {
            adapter = new GroupDynamicAdapter(getActivity(), datas);
            adapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {

                }
            });
            initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        } else {
            getDataBack(adapter.getDatas(), datas, adapter);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //我的组合
                List<GroupItem> datas = GsonUtil.getInstance().toList(data, GroupItem.class);
                initAllMyGroup(datas);
                break;
            case 0x124:
                //排行
                if (index == 1) {
                    list7 = GsonUtil.getInstance().toObj(data, GroupRank.class);
                } else if (index == 2) {
                    list30 = GsonUtil.getInstance().toObj(data, GroupRank.class);
                } else if (index == 3) {
                    listall = GsonUtil.getInstance().toObj(data, GroupRank.class);
                    if (hotGroupAdapter == null) {
                        initHotList(list7);
                    }
                }
                if (index < 3) {
                    addRequest(binder.top(types[index++], this));
                } else if (index == 3) {
                    index = 0;
                }
                break;
            case 0x125:
                List<GroupDynamic> groupDynamics = GsonUtil.getInstance().toList(data, GroupDynamic.class);
                initList(groupDynamics);
                break;
        }
    }

    @Override
    protected void refreshData() {
        if (userLogin != null) {
            addRequest(binder.listDemo(this));
            addRequest(binder.dynamic(this));
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        } else {
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
