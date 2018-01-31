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
import com.xiaomiquan.mvp.databinder.group.GroupDealBinder;
import com.xiaomiquan.mvp.delegate.group.GroupDealDelegate;
import com.xiaomiquan.mvp.fragment.group.CurrencyFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.NotDealFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealActivity extends BaseDataBindActivity<GroupDealDelegate, GroupDealBinder> {
    CurrencyFragment currencyFragmentBuy;
    CurrencyFragment currencyFragmentSell;

    @Override
    public GroupDealBinder getDataBinder(GroupDealDelegate viewDelegate) {
        return new GroupDealBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("组合交易"));
        initTablelayout();
        initCurrency();
    }

    @Override
    protected Class<GroupDealDelegate> getDelegateClass() {
        return GroupDealDelegate.class;
    }

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private void initTablelayout() {
        mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_deal));
        fragments = new ArrayList<>();
        fragments.add(new NotDealFragment());
        fragments.add(new HistoryEntrustFragment());
        fragments.add(new HistoryTradingFragment());
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
        viewDelegate.viewHolder.vp_sliding.setAdapter(new InnerPagerAdapter(getSupportFragmentManager(), fragments, mTitles.toArray(new String[mTitles.size()])));
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
                viewDelegate.viewHolder.vp_sliding.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    public void initCurrency() {
        currencyFragmentBuy = new CurrencyFragment();
        currencyFragmentSell = new CurrencyFragment();
        viewDelegate.initAddFragment(R.id.fl_currency, getSupportFragmentManager());
        viewDelegate.addFragment(currencyFragmentBuy);
        viewDelegate.addFragment(currencyFragmentSell);
        viewDelegate.showFragment(0);
    }

}
