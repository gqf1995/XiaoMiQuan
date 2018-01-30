package com.xiaomiquan.mvp.fragment.circle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.FragmentTabViewpageBinder;
import com.xiaomiquan.mvp.delegate.FragmentTabViewpageDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareFragment extends BaseDataBindFragment<FragmentTabViewpageDelegate, FragmentTabViewpageBinder> {
    ArrayList<Fragment> fragments;
    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected Class<FragmentTabViewpageDelegate> getDelegateClass() {
        return FragmentTabViewpageDelegate.class;
    }

    @Override
    public FragmentTabViewpageBinder getDataBinder(FragmentTabViewpageDelegate viewDelegate) {
        return new FragmentTabViewpageBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initTablelayout();
    }

    private void initTablelayout() {
        mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_square));
        fragments = new ArrayList<>();
        fragments.add(new LiveFragment());
        fragments.add(new ArticleFragment());
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);

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

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                break;
        }
    }

}
