package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.CombinationBinder;
import com.xiaomiquan.mvp.delegate.CombinationDelegate;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.GroupNotDealFragment;

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
        //修改简介
        EditIntroductionActivity.startAct(this, groupItem.getId(), groupItem.getBrief());
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
        //addRequest(binder.getTodayInfo(groupItem.getId(), this));
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(groupItem.getName()).setSubTitle(isMy ? CommonUtils.getString(R.string.str_change_introduction) : ""));
        initViews();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //今日收益&日均操作次数
                addRequest(binder.adllRate(groupItem.getId(), this));
                break;
            case 0x124:
                //分期收益
                addRequest(binder.rateTrend(groupItem.getId(), this));
                break;
            case 0x125:
                //收益走势

                break;
        }
    }

    private void initViews() {
        String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_combination);
        fragments = new ArrayList<>();
        fragments.add(GroupDetailListFragment.newInstance(groupItem.getId()));
        fragments.add(GroupNotDealFragment.newInstance(groupItem.getId()));
        fragments.add(GroupHistoryTradingFragment.newInstance(groupItem.getId()));
        fragments.add(GroupHistoryEntrustFragment.newInstance(groupItem.getId()));
        for (int i = 0; i < stringArray.length; i++) {
            mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
        InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, stringArray);
        viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.viewpager);
    }

}
