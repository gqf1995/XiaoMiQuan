package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.EarningsMovements;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.group.MyAccountBinder;
import com.xiaomiquan.mvp.delegate.group.MyAccountDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.utils.BigUIUtil;

import java.util.List;

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
        initToolbar(new ToolbarBuilder().setTitle(""));
        initView();
        getIntentData();
    }

    private void initView() {
        viewDelegate.setOnClickListener(this,
                R.id.lin_gameplay_introduced,
                R.id.lin_revenue_ranking,
                R.id.tv_assets_report
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
        addRequest(binder.listDemo(this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //今日收益&日均操作次数
                BigUIUtil.getinstance().rateTextView(Double.parseDouble(GsonUtil.getInstance().getValue(data, "todayRate")), viewDelegate.viewHolder.tv_today_earnings);
                viewDelegate.viewHolder.tv_daily_operation.setText(GsonUtil.getInstance().getValue(data, "count"));
                addRequest(binder.rateTrend(groupItem.getId(), this));
                break;
            case 0x124:
                //分期收益
                viewDelegate.initRate(data);
                break;
            case 0x125:
                //收益走势
                EarningsMovements earningsMovements = GsonUtil.getInstance().toObj(data, EarningsMovements.class);
                viewDelegate.initEarningsMovements(earningsMovements);
                addRequest(binder.allRate(groupItem.getId(), this));
                break;
                case 0x126:
                    //我的组合
                    List<GroupItem> groupItems = GsonUtil.getInstance().toList(data, GroupItem.class);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_gameplay_introduced:

                break;
            case R.id.lin_revenue_ranking:
                startActivity(new Intent(MyAccountActivity.this, RevenueRankingActivity.class));
                break;
            case R.id.tv_assets_report:
                MyPropertyDetailActivity.startAct(MyAccountActivity.this, groupItem);
                break;
        }
    }
}
