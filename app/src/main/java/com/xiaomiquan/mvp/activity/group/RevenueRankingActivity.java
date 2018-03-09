package com.xiaomiquan.mvp.activity.group;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.group.RevenueRankingBinder;
import com.xiaomiquan.mvp.delegate.group.RevenueRankingDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.GroupNotDealFragment;
import com.xiaomiquan.mvp.fragment.group.RevenueRankingFragment;

import java.util.ArrayList;
import java.util.List;

public class RevenueRankingActivity extends BaseDataBindActivity<RevenueRankingDelegate, RevenueRankingBinder> {

    ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    InnerPagerAdapter innerPagerAdapter;

    @Override
    protected Class<RevenueRankingDelegate> getDelegateClass() {
        return RevenueRankingDelegate.class;
    }

    @Override
    public RevenueRankingBinder getDataBinder(RevenueRankingDelegate viewDelegate) {
        return new RevenueRankingBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
        initView();
    }

    private void initView() {
        if (!ListUtils.isEmpty(getSupportFragmentManager().getFragments())) {
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_rank);
            fragments = new ArrayList<>();
            for (int i = 0; i < stringArray.length; i++) {
                fragments.add(RevenueRankingFragment.newInstance(i + ""));
                mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            }
            viewDelegate.viewHolder.tl.setTabData(mTabEntities);
            innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, stringArray);
            viewDelegate.viewHolder.tl.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.vp_sliding);
        }
        viewDelegate.viewHolder.vp_sliding.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                RevenueRankingFragment.type=position+"";
            }
            @Override
            public void onPageScrollStateChanged(int state) {
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
