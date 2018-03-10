package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.TeamInfo;
import com.xiaomiquan.mvp.databinder.group.MyAccountBinder;
import com.xiaomiquan.mvp.delegate.group.MyAccountDelegate;

public class MyAccountActivity extends BaseDataBindActivity<MyAccountDelegate, MyAccountBinder> {

    @Override
    protected Class<MyAccountDelegate> getDelegateClass() {
        return MyAccountDelegate.class;
    }

    @Override
    public MyAccountBinder getDataBinder(MyAccountDelegate viewDelegate) {
        return new MyAccountBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_group)));
        initView();
        getIntentData();
    }

    private void initView() {
        viewDelegate.setOnClickListener(this,
                R.id.lin_gameplay_introduced,
                R.id.lin_revenue_ranking,
                R.id.lin_assets_report
        );
    }

    public static void startAct(Activity activity,
                                GroupItem groupItem
    ) {
        Intent intent = new Intent(activity, MyAccountActivity.class);
        intent.putExtra("groupItem", groupItem);
        activity.startActivity(intent);
    }

    private GroupItem groupItem;

    private void getIntentData() {
        Intent intent = getIntent();
        groupItem = intent.getParcelableExtra("groupItem");
        addRequest(binder.demoInfo(groupItem.getId(), MyAccountActivity.this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                TeamInfo teamInfo = GsonUtil.getInstance().toObj(data, TeamInfo.class);
                viewDelegate.initTeaminfo(teamInfo);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_gameplay_introduced:
                //玩法规则
                break;
            case R.id.lin_revenue_ranking:
                //排行
                startActivity(new Intent(MyAccountActivity.this, RevenueRankingActivity.class));
                break;
            case R.id.lin_assets_report:
                //资产明细
                MyPropertyDetailActivity.startAct(MyAccountActivity.this, groupItem);
                break;
        }
    }
}
