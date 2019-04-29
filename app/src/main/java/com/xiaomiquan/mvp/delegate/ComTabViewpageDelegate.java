package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.CommonTabLayout;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;

import java.util.ArrayList;
import java.util.List;

public class ComTabViewpageDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_comtab_viewpage;
    }

    public void initViewpager(List<Fragment> fragments, ArrayList<CustomTabEntity> mTabEntities, FragmentManager fragmentManager, List<String> mTitles) {
        viewHolder.vp_sliding.setAdapter(new InnerPagerAdapter(fragmentManager, (ArrayList) fragments, mTitles.toArray(new String[mTitles.size()])));
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.vp_sliding.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewHolder.tl_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewHolder.vp_sliding.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public static class ViewHolder {
        public View rootView;
        public CommonTabLayout tl_2;
        public ViewPager vp_sliding;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
        }

    }
}