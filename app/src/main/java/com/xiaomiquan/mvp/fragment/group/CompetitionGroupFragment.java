package com.xiaomiquan.mvp.fragment.group;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyTeamAdapter;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.AddTeamActivity;
import com.xiaomiquan.mvp.activity.group.CreatTeamActivity;
import com.xiaomiquan.mvp.activity.group.InviteFriendsActivity;
import com.xiaomiquan.mvp.activity.group.ManagementTeamActivity;
import com.xiaomiquan.mvp.activity.group.TeamDetailActivity;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.mvp.databinder.group.CompetitionGroupBinder;
import com.xiaomiquan.mvp.delegate.group.CompetitionGroupDelegate;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.xiaomiquan.base.AppConst.myRewardUrl;
import static com.xiaomiquan.base.AppConst.rulesUrl;

/**
 * 组合大赛
 */
public class CompetitionGroupFragment extends BaseDataBindFragment<CompetitionGroupDelegate, CompetitionGroupBinder> {

    MyTeamAdapter myCreateTeam;
    MyTeamAdapter myJoinTeam;
    List<HotTeam> datas;
    String type;

    @Override
    public CompetitionGroupBinder getDataBinder(CompetitionGroupDelegate viewDelegate) {
        return new CompetitionGroupBinder(viewDelegate);
    }

    @Override
    protected void onServiceError(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                HotTeam hotTeam = GsonUtil.getInstance().toObj(data, "gameTeam", HotTeam.class);
                String gameOverTime = GsonUtil.getInstance().getValue(data, "gameOverTime");
                viewDelegate.viewHolder.tv_end_time.setText(CommonUtils.getString(R.string.str_closing_time) + "  " + gameOverTime + "");
                type = GsonUtil.getInstance().getValue(data, "type");
                if (hotTeam == null) {
                    return;
                }
                datas = new ArrayList<>();
                //String myRewardUrl = GsonUtil.getInstance().getValue(data, "myRewardUrl");
                //String activityRuleUrl = GsonUtil.getInstance().getValue(data, "activityRuleUrl");
                datas.add(hotTeam);
                if ("1".equals(type)) {
                    //我的创建
                    viewDelegate.viewHolder.lin_my_join.setVisibility(View.GONE);
                    initMyCreateTeam(datas);
                } else if ("2".equals(type)) {
                    //我的加入
                    viewDelegate.viewHolder.lin_my_create.setVisibility(View.GONE);
                    initMyJoinTeam(datas);
                } else if ("3".equals(type)) {
                    //没有
                    viewDelegate.viewHolder.lin_my_create.setVisibility(View.GONE);
                }
                break;
        }
    }


    private void initMyCreateTeam(List<HotTeam> datas) {
        if (myCreateTeam == null) {
            myCreateTeam = new MyTeamAdapter(getActivity(), datas);
            myCreateTeam.setText(CommonUtils.getString(R.string.str_management));
            myCreateTeam.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    //战队管理
                    gotoActivity(ManagementTeamActivity.class).startAct();
                }
            });
            viewDelegate.viewHolder.rv_my_create_team.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_my_create_team.setAdapter(myCreateTeam);
            viewDelegate.viewHolder.lin_my_create.setVisibility(View.GONE);
        } else {
            myCreateTeam.setDatas(datas);
            viewDelegate.viewHolder.lin_my_create.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            onRefreshDatas();
        }
    }

    public void notifyDataSetChanged() {
        if (myJoinTeam != null && myCreateTeam != null) {
            myCreateTeam.notifyDataSetChanged();
            myJoinTeam.notifyDataSetChanged();
        }
    }

    private void initMyJoinTeam(List<HotTeam> datas) {
        if (myJoinTeam == null) {
            myJoinTeam = new MyTeamAdapter(getActivity(), datas);
            myJoinTeam.setText(CommonUtils.getString(R.string.str_details));
            myJoinTeam.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    //查看战队详情
                    TeamDetailActivity.startAct(getActivity(), myJoinTeam.getDatas().get(position).getId() + "");
                }
            });
            viewDelegate.viewHolder.rv_my_join_team.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_my_join_team.setAdapter(myJoinTeam);
            viewDelegate.viewHolder.lin_my_join.setVisibility(View.GONE);
        } else {
            myJoinTeam.setDatas(datas);
            viewDelegate.viewHolder.lin_my_join.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initMyCreateTeam(new ArrayList<HotTeam>());
        initMyJoinTeam(new ArrayList<HotTeam>());
        viewDelegate.setOnClickListener(this,
                R.id.lin_add_team,
                R.id.lin_create_team,
                R.id.lin_my_reward,
                R.id.lin_activity_rules,
                R.id.lin_invite_friends
        );
        onRefreshDatas();
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshDatas();
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_add_team:
                AddTeamActivity.startAct(this, "", 0x124);
                break;
            case R.id.lin_create_team:
                //创建战队
                if (SingSettingDBUtil.getUserLogin() != null) {
                    if ("3".equals(type)) {
                        CreatTeamActivity.startAct(this, 0x123);
                    } else {
                        ToastUtil.show(CommonUtils.getString(R.string.str_toast_have_team));
                        return;
                    }
                } else {
                    ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                    return;
                }
                break;
            case R.id.lin_my_reward:
                //我的奖励
                WebActivityActivity.startAct(getActivity(), myRewardUrl);
                break;
            case R.id.lin_activity_rules:
                //比赛规则
                WebActivityActivity.startAct(getActivity(), rulesUrl);
                break;
            case R.id.lin_invite_friends:
                //邀请好友
                gotoActivity(InviteFriendsActivity.class).startAct();
                break;
        }
    }

    public void onRefreshDatas() {
        addRequest(binder.getBigGame(this));
    }

    @Override
    protected Class<CompetitionGroupDelegate> getDelegateClass() {
        return CompetitionGroupDelegate.class;
    }


}
