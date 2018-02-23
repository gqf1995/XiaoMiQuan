package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.popupWindow.IconTextPopWindow;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.AllGroupMyGroupAdapter;
import com.xiaomiquan.adapter.group.GroupDynamicAdapter;
import com.xiaomiquan.adapter.group.HotGroupAdapter;
import com.xiaomiquan.adapter.group.HotTeamAdapter;
import com.xiaomiquan.entity.bean.group.AllGroupData;
import com.xiaomiquan.entity.bean.group.GroupDynamic;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.AddTeamActivity;
import com.xiaomiquan.mvp.activity.group.AllTeamActivity;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.activity.group.CreatGroupActivity;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.activity.group.TeamDetailActivity;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.AllGroupDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部组合
 */
public class AllGroupFragment extends BasePullFragment<AllGroupDelegate, BaseFragmentPullBinder> {
    AllGroupMyGroupAdapter allMyGroupAdapter;
    HotGroupAdapter hotGroupAdapter;
    String[] types = {"1", "2", "3"};

    //UserLogin userLogin;
    GroupDynamicAdapter adapter;
    HotTeamAdapter hotTeamAdapter;
    AllGroupData allGroupData;

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

    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
    }

    public void notifyDataSetChanged() {
        if (allMyGroupAdapter != null && hotGroupAdapter != null && adapter != null) {
            allMyGroupAdapter.notifyDataSetChanged();
            hotGroupAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }

    private void initHotTeam(List<HotTeam> dats) {
        if (hotTeamAdapter == null) {
            hotTeamAdapter = new HotTeamAdapter(getActivity(), dats);
            hotTeamAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if(view.getId()==R.id.fl_root||view.getId()==R.id.tv_apply_to_join){
                        TeamDetailActivity.startAct(getActivity(), hotTeamAdapter.getDatas().get(position).getId() + "");
                    }else if(view.getId()==R.id.ic_pic){
                        PersonalHomePageActivity.startAct(getActivity(),hotTeamAdapter.getDatas().get(position).getOwnerId()+"");
                    }
                }
            });
            viewDelegate.viewHolder.rcv_hot_team.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rcv_hot_team.setAdapter(hotTeamAdapter);
            viewDelegate.viewHolder.tv_more_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //更多战队
                    gotoActivity(AllTeamActivity.class).startAct();
                }
            });
        } else {
            hotTeamAdapter.setDatas(dats);
        }
    }

    IconTextPopWindow iconTextPopWindow;


    private void addOrCreateTeam() {
        if (iconTextPopWindow == null) {
            iconTextPopWindow = new IconTextPopWindow(getActivity());
            List<IconTextPopWindow.Entity> datas = new ArrayList<>();
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_add_or_create_team);
            datas.add(new IconTextPopWindow.Entity(R.drawable.create_team, stringArray[0]));
            datas.add(new IconTextPopWindow.Entity(R.drawable.join_team, stringArray[1]));
            iconTextPopWindow.setDatas(datas);
            iconTextPopWindow.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (position == 0) {
                        //创建战队
                        CreatGroupActivity.startAct(AllGroupFragment.this, 0x123);
                    } else if (position == 1) {
                        //加入战队
                        AddTeamActivity.startAct(AllGroupFragment.this, "", 0x123);
                    }
                }
            });
        }
        iconTextPopWindow.showAtLocation(viewDelegate.viewHolder.pull_recycleview, Gravity.BOTTOM, 0, 0);
    }


    private void initAllMyGroup(List<GroupItem> datas) {
        datas.add(null);
        if (allMyGroupAdapter == null) {
            allMyGroupAdapter = new AllGroupMyGroupAdapter(getActivity(), datas);
            allMyGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (SingSettingDBUtil.getUserLogin() != null) {
                        if (view.getId() == R.id.tv_commit) {
                            //立即交易
                            List<GroupItem> datas = new ArrayList<>();
                            datas.addAll(allMyGroupAdapter.getDatas());
                            datas.remove(datas.size() - 1);
                            GroupDealActivity.startAct(getActivity(), (ArrayList) datas, position, true);
                        } else if (view.getId() == R.id.lin_add) {
                            //创建账户 或者 加入战队
                            addOrCreateTeam();
                        }
                    }else{
                        ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                    }
                }
            });
            viewDelegate.viewHolder.rv_my_group.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_my_group.setAdapter(allMyGroupAdapter);
            viewDelegate.viewHolder.lin_my_group.setVisibility(View.VISIBLE);
        } else {
            allMyGroupAdapter.setDatas(datas);
        }
    }

    private void initHotList(List<GroupItem> tops) {
        if (hotGroupAdapter == null) {
            hotGroupAdapter = new HotGroupAdapter(getActivity(), tops);
            hotGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (hotGroupAdapter.getDatas().get(position).getIsAttention() == 1) {
                        //查看详情
                        CombinationActivity.startAct(getActivity(), hotGroupAdapter.getDatas().get(position), false);
                    } else if (hotGroupAdapter.getDatas().get(position).getIsAttention() == 0) {
                        if (SingSettingDBUtil.getUserLogin() != null) {
                            for (int i = 0; i < hotGroupAdapter.getDatas().size(); i++) {
                                if (allGroupData.getTopWeeks().get(i).getId().equals(hotGroupAdapter.getDatas().get(position).getId())) {
                                    allGroupData.getTopWeeks().get(i).setIsAttention(1);
                                }
                                if (allGroupData.getTopMonth().get(i).getId().equals(hotGroupAdapter.getDatas().get(position).getId())) {
                                    allGroupData.getTopWeeks().get(i).setIsAttention(1);
                                }
                                if (allGroupData.getTopTotal().get(i).getId().equals(hotGroupAdapter.getDatas().get(position).getId())) {
                                    allGroupData.getTopWeeks().get(i).setIsAttention(1);
                                }
                            }
                            hotGroupAdapter.getDatas().get(position).setIsAttention(1);
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
                    if (allGroupData == null) {
                        return;
                    }
                    if (position == 0) {
                        initHotList(allGroupData.getTopWeeks());
                    } else if (position == 1) {
                        initHotList(allGroupData.getTopMonth());
                    } else {
                        initHotList(allGroupData.getTopMonth());
                    }
                }

                @Override
                public void onTabReselect(int position) {

                }
            });
        } else {
            hotGroupAdapter.setDatas(tops);
        }
    }

    private void initList(List<GroupDynamic> datas) {
        if (adapter == null) {
            adapter = new GroupDynamicAdapter(getActivity(), datas);
            adapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    GroupDealActivity.startAct(getActivity(), (ArrayList) adapter.getDatas(), position, true);
                }
            });
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
            onRefresh();
        } else {
            getDataBack(adapter.getDatas(), datas, adapter);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<GroupDynamic> list = GsonUtil.getInstance().toList(data, GroupDynamic.class);
                initList(list);
                break;
            case 0x124://bkg klc wd
                //我的组合
                allGroupData = GsonUtil.getInstance().toObj(data, AllGroupData.class);
                initHotTeam(allGroupData.getHotTeams());
                initAllMyGroup(allGroupData.getUserDemoList());
                initHotList(allGroupData.getTopWeeks());
                initList(allGroupData.getUserDemoDynamicList());
                viewDelegate.setBanner(allGroupData.getTurnPicturesList());
                break;
        }
    }

    @Override
    protected void refreshData() {
        if (viewDelegate.page == viewDelegate.defaultPage) {
            addRequest(binder.getSquareTeamGame(this));
        } else {
            addRequest(binder.dynamic(this));
        }
    }
}
