package com.xiaomiquan.mvp.activity.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.adapter.group.RankTeampeopleAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.TeamDetailBinder;
import com.xiaomiquan.mvp.delegate.TeamDetailDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailActivity extends BaseDataBindActivity<TeamDetailDelegate, TeamDetailBinder> {

    MyGroupAdapter myGroupAdapter;
    RankTeampeopleAdapter rankTeampeopleAdapter;

    @Override
    protected Class<TeamDetailDelegate> getDelegateClass() {
        return TeamDetailDelegate.class;
    }

    @Override
    public TeamDetailBinder getDataBinder(TeamDetailDelegate viewDelegate) {
        return new TeamDetailBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.str_apply_to_join_team)));
        initRankTeam(new ArrayList<String>());
    }

    private void initRankTeam(List<String> datas) {
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        rankTeampeopleAdapter = new RankTeampeopleAdapter(this, datas);
        viewDelegate.viewHolder.rv_team_rank.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_team_rank.setAdapter(rankTeampeopleAdapter);

    }

    private void initMyGroup(List<GroupItem> datas) {
        if (myGroupAdapter == null) {
            myGroupAdapter = new MyGroupAdapter(this, datas);
            myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, final int position, Object item) {
                    if (view.getId() == R.id.tv_deal) {
                        GroupDealActivity.startAct(TeamDetailActivity.this, (ArrayList) myGroupAdapter.getDatas(), position, true);
                    }
                    if (view.getId() == R.id.tv_look) {
                        CombinationActivity.startAct(TeamDetailActivity.this, myGroupAdapter.getDatas().get(position), true);
                    }
                }
            });
            viewDelegate.viewHolder.rv_my_group.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_my_group.setAdapter(myGroupAdapter);
        } else {
            myGroupAdapter.setDatas(datas);
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        CircleDialogHelper.initDefaultInputDialog(this,
                CommonUtils.getString(R.string.str_apply_to_join_team_reason),
                CommonUtils.getString(R.string.str_toast_input_reason),
                CommonUtils.getString(R.string.str_determine),
                new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        //申请加入战队

                    }
                }
        ).show();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
