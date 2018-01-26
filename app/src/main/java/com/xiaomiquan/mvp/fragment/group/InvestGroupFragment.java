package com.xiaomiquan.mvp.fragment.group;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.group.CreatGroupActivity;
import com.xiaomiquan.mvp.databinder.group.InvestGroupBinder;
import com.xiaomiquan.mvp.delegate.FragmentTabViewpageDelegate;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvestGroupFragment extends BaseDataBindFragment<TabViewpageDelegate, InvestGroupBinder> {

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    GroupChangeFragment groupChangeFragment;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected Class<TabViewpageDelegate> getDelegateClass() {
        return TabViewpageDelegate.class;
    }

    @Override
    public InvestGroupBinder getDataBinder(TabViewpageDelegate viewDelegate) {
        return new InvestGroupBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("投资组合").setSubTitle("创建组合"));
        initTablelayout();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        gotoActivity(CreatGroupActivity.class).startAct();
    }

    @Override

    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    private void initTablelayout() {
        mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_group));
        fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            fragments.add(new GroupChangeFragment());
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles.toArray(new String[mTitles.size()]), (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);
        viewDelegate.viewHolder.vp_sliding.setAdapter(new InnerPagerAdapter(getChildFragmentManager(), fragments, mTitles.toArray(new String[mTitles.size()])));
        viewDelegate.viewHolder.vp_sliding.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                viewDelegate.viewHolder.vp_sliding.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

}
