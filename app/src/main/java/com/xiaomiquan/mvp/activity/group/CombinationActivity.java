package com.xiaomiquan.mvp.activity.group;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.CombinationBinder;
import com.xiaomiquan.mvp.delegate.CombinationDelegate;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.GroupNotDealFragment;

import java.util.ArrayList;

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

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.str_change_introduction)));
        String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_combination);
        fragments = new ArrayList<>();
        fragments.add(new GroupDetailListFragment());
        fragments.add(new GroupNotDealFragment());
        fragments.add(new GroupHistoryTradingFragment());
        fragments.add(new GroupHistoryEntrustFragment());

        for (int i = 0; i < stringArray.length; i++) {
            mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
        viewDelegate.viewHolder.viewpager.setAdapter(new InnerPagerAdapter(getSupportFragmentManager(), fragments, stringArray));
        viewDelegate.viewHolder.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewDelegate.viewHolder.tl_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewDelegate.viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.showFragment(position);
                viewDelegate.viewHolder.viewpager.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
