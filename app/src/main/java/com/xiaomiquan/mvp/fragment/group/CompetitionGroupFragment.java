package com.xiaomiquan.mvp.fragment.group;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyTeamAdapter;
import com.xiaomiquan.mvp.activity.group.AddTeamActivity;
import com.xiaomiquan.mvp.activity.group.CreatTeamActivity;
import com.xiaomiquan.mvp.activity.group.InviteFriendsActivity;
import com.xiaomiquan.mvp.activity.group.ManagementTeamActivity;
import com.xiaomiquan.mvp.activity.group.TeamDetailActivity;
import com.xiaomiquan.mvp.databinder.group.CompetitionGroupBinder;
import com.xiaomiquan.mvp.delegate.group.CompetitionGroupDelegate;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 组合大赛
 */
public class CompetitionGroupFragment extends BaseDataBindFragment<CompetitionGroupDelegate, CompetitionGroupBinder> {

    MyTeamAdapter myCreateTeam;
    MyTeamAdapter myJoinTeam;

    @Override
    public CompetitionGroupBinder getDataBinder(CompetitionGroupDelegate viewDelegate) {
        return new CompetitionGroupBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                break;
            case 0x124:
                break;
        }
    }


    private void initMyCreateTeam(List<String> datas) {
        if (myCreateTeam == null) {
            for (int i = 0; i < 5; i++) {
                datas.add("");
            }
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
        } else {
            myCreateTeam.setDatas(datas);
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

    private void initMyJoinTeam(List<String> datas) {
        if (myJoinTeam == null) {
            for (int i = 0; i < 5; i++) {
                datas.add("");
            }
            myJoinTeam = new MyTeamAdapter(getActivity(), datas);
            myCreateTeam.setText(CommonUtils.getString(R.string.str_details));
            myJoinTeam.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    //查看战队详情
                    gotoActivity(TeamDetailActivity.class).startAct();
                }
            });
            viewDelegate.viewHolder.rv_my_join_team.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_my_join_team.setAdapter(myJoinTeam);
        } else {
            myJoinTeam.setDatas(datas);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initMyCreateTeam(new ArrayList<String>());
        initMyJoinTeam(new ArrayList<String>());
        viewDelegate.setOnClickListener(this,
                R.id.lin_add_team,
                R.id.lin_create_team,
                R.id.lin_my_reward,
                R.id.lin_activity_rules,
                R.id.lin_invite_friends
        );
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
                AddTeamActivity.startAct(this, 0x124);
                break;
            case R.id.lin_create_team:
                //创建战队
                CreatTeamActivity.startAct(this, 0x123);
                break;
            case R.id.lin_my_reward:
                break;
            case R.id.lin_activity_rules:
                break;
            case R.id.lin_invite_friends:
                //邀请好友
                gotoActivity(InviteFriendsActivity.class).startAct();
                break;
        }
    }

    public void onRefreshDatas() {

    }

    @Override
    protected Class<CompetitionGroupDelegate> getDelegateClass() {
        return CompetitionGroupDelegate.class;
    }


}
