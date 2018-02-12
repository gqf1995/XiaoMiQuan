package com.xiaomiquan.mvp.activity.group;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.HotTeamAdapter;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;

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
            initRecycleViewPull(hotTeamAdapter, new LinearLayoutManager(this));
            onRefresh();
        } else {
            getDataBack(hotTeamAdapter.getDatas(), dats, hotTeamAdapter);
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<HotTeam> list = GsonUtil.getInstance().toList(data, HotTeam.class);
                initList(list);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.listHotGameTeam(this));
    }
}
