package com.xiaomiquan.mvp.activity.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.HotTeamAdapter;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;

import java.util.ArrayList;
import java.util.List;

public class AllTeamActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    HotTeamAdapter hotTeamAdapter;

    @Override
    protected Class<BaseActivityPullDelegate> getDelegateClass() {
        return BaseActivityPullDelegate.class;
    }

    @Override
    public BaseActivityPullBinder getDataBinder(BaseActivityPullDelegate viewDelegate) {
        return new BaseActivityPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_all_team)));
        initList(new ArrayList<HotTeam>());
    }

    private void initList(List<HotTeam> dats) {
        if (hotTeamAdapter == null) {
            hotTeamAdapter = new HotTeamAdapter(this, dats);
            hotTeamAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    joinTeam();
                }
            });
            initRecycleViewPull(hotTeamAdapter, new LinearLayoutManager(this));
        } else {
            getDataBack(hotTeamAdapter.getDatas(), dats, hotTeamAdapter);
        }

    }

    private void joinTeam() {
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

    @Override
    protected void refreshData() {

    }
}
