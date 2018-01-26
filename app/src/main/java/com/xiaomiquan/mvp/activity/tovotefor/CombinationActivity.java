package com.xiaomiquan.mvp.activity.tovotefor;

import android.support.design.widget.AppBarLayout;
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
import com.xiaomiquan.mvp.fragment.CombinationFragment;

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
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
        String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_combination);
        fragments = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            fragments.add(CombinationFragment.newInstance(stringArray[i]));
            mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            //fragments.add(CombinationFragment.newInstance());
        }

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
        viewDelegate.viewHolder.appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset >= 0) {
//                    viewHolder.swipeRefreshLayout.setEnabled(true);
//                } else {
//                    viewHolder.swipeRefreshLayout.setEnabled(false);
//                }
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
