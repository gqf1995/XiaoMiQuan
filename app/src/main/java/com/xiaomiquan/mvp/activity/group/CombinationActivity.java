package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.EarningsMovements;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.CombinationBinder;
import com.xiaomiquan.mvp.delegate.CombinationDelegate;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.GroupNotDealFragment;
import com.xiaomiquan.utils.BigUIUtil;

import java.util.ArrayList;

/**
 * 组合详情
 */
public class CombinationActivity extends BaseDataBindActivity<CombinationDelegate, CombinationBinder> {
    ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected Class<CombinationDelegate> getDelegateClass() {
        return CombinationDelegate.class;
    }

    @Override
    public CombinationBinder getDataBinder(CombinationDelegate viewDelegate) {
        return new CombinationBinder(viewDelegate);
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (isMy) {
            //修改简介
            EditIntroductionActivity.startAct(this, groupItem.getId(), groupItem.getBrief(), groupItem.getSync(), 0x123);
        } else {
            //取消关注 或 关注
            if (groupItem.getIsAttention() == 0) {
                binder.cancelAttention(groupItem.getId(), null);
                viewDelegate.getmToolbarSubTitle().setText(CommonUtils.getString(R.string.str_focuse));
            } else if (groupItem.getIsAttention() == 1) {
                binder.demoattention(groupItem.getUserId(), null);
                viewDelegate.getmToolbarSubTitle().setText(CommonUtils.getString(R.string.str_cancel_fucose));
            } else if (groupItem.getIsAttention() == 2) {

            }
        }
    }

    public static void startAct(Activity activity,
                                GroupItem groupItem,
                                boolean isMy
    ) {
        Intent intent = new Intent(activity, CombinationActivity.class);
        intent.putExtra("groupItem", groupItem);
        intent.putExtra("isMy", isMy);
        activity.startActivity(intent);
    }

    private GroupItem groupItem;
    boolean isMy;

    private void getIntentData() {
        Intent intent = getIntent();
        groupItem = intent.getParcelableExtra("groupItem");
        isMy = intent.getBooleanExtra("isMy", false);
        viewDelegate.initData(groupItem);
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getTodayInfo(isMy ? groupItem.getId() : groupItem.getUserId(), CombinationActivity.this));
            }
        });
        addRequest(binder.getTodayInfo(isMy ? groupItem.getId() : groupItem.getUserId(), this));
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(groupItem.getName()).setSubTitle(isMy ? CommonUtils.getString(R.string.str_change_introduction) : CommonUtils.getString(R.string.str_cancel_fucose)));
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                String brief = data.getStringExtra("brief");
                String sync = data.getStringExtra("sync");
                groupItem.setBrief(brief);
                groupItem.setSync(sync);
                viewDelegate.initData(groupItem);
            }
        }
    }

    @Override
    protected void onServiceError(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                //今日收益&日均操作次数
                BigUIUtil.getinstance().rateTextView(Double.parseDouble(GsonUtil.getInstance().getValue(data, "todayRate")), viewDelegate.viewHolder.tv_today_earnings);
                viewDelegate.viewHolder.tv_daily_operation.setText(GsonUtil.getInstance().getValue(data, "count"));
                addRequest(binder.rateTrend(isMy ? groupItem.getId() : groupItem.getUserId(), this));
                break;
            case 0x124:
                //分期收益
                viewDelegate.initRate(data);
                break;
            case 0x125:
                //收益走势
                EarningsMovements earningsMovements = GsonUtil.getInstance().toObj(data, EarningsMovements.class);
                viewDelegate.initEarningsMovements(earningsMovements);
                addRequest(binder.allRate(isMy ? groupItem.getId() : groupItem.getUserId(), this));
                break;
        }
    }

    private void initViews() {
        if (!ListUtils.isEmpty(getSupportFragmentManager().getFragments())) {
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_combination);
            fragments = new ArrayList<>();
            fragments.add(GroupDetailListFragment.newInstance(isMy ? groupItem.getId() : groupItem.getUserId()));
            fragments.add(GroupNotDealFragment.newInstance(isMy ? groupItem.getId() : groupItem.getUserId()));
            fragments.add(GroupHistoryTradingFragment.newInstance(isMy ? groupItem.getId() : groupItem.getUserId()));
            fragments.add(GroupHistoryEntrustFragment.newInstance(isMy ? groupItem.getId() : groupItem.getUserId()));
            for (int i = 0; i < stringArray.length; i++) {
                mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            }
            viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
            InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, stringArray);
            viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.viewpager);

        }
    }

}
