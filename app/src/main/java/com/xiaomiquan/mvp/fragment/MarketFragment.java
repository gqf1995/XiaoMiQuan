package com.xiaomiquan.mvp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.activity.market.SearchCoinMarketActivity;
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.widget.GainsTabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_EXCHANGENAME;

public class MarketFragment extends BaseDataBindFragment<TabViewpageDelegate, TabViewpageBinder> {
    ArrayList<Fragment> fragments;
    List<String> mTitles;
    GainsTabView gainsTabView;
    List<ExchangeName> exchangeNameList;

    @Override
    protected Class<TabViewpageDelegate> getDelegateClass() {
        return TabViewpageDelegate.class;
    }

    @Override
    public TabViewpageBinder getDataBinder(TabViewpageDelegate viewDelegate) {
        return new TabViewpageBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setmRightImg2(CommonUtils.getString(R.string.ic_Notifications)).setmRightImg1(CommonUtils.getString(R.string.ic_Filter2))
                .setTitle(CommonUtils.getString(R.string.str_title_market)).setShowBack(true));
        viewDelegate.setBackIconFontText(CommonUtils.getString(R.string.ic_Search1));
        initBarClick();
        //网络获取交易所 名称
        String exchangeNamesStr = CacheUtils.getInstance().getString(CACHE_EXCHANGENAME);
        if (!TextUtils.isEmpty(exchangeNamesStr)) {
            exchangeNameList = GsonUtil.getInstance().toList(exchangeNamesStr, ExchangeName.class);
            initTablelayout(exchangeNameList);
        }
        addRequest(binder.getAllEXchange(this));


    }

    @Override
    protected void clickRightIv() {
        super.clickRightIv();
        // 排序
        gotoActivity(SortingUserCoinActivity.class).startAct();
    }

    @Override
    protected void clickRightIv1() {
        super.clickRightIv1();
        // 通知
    }

    private void initTablelayout(List<ExchangeName> exchangeNames) {
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        List<String> strings = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_market));
        fragments.add(new UserChooseFragment());
        fragments.add(new ComprehensiveFragment());
        fragments.add(new MarketValueFragment());
        mTitles.add(strings.get(0));
        mTitles.add(strings.get(1));
        mTitles.add(strings.get(2));
        for (int i = 0; i < exchangeNames.size(); i++) {
            mTitles.add(exchangeNames.get(i).getEname());
            fragments.add(ExchangeFragment.newInstance(exchangeNames.get(i)));
        }
        viewDelegate.viewHolder.vp_sliding.setOffscreenPageLimit(1);
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles.toArray(new String[mTitles.size()]), (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);

        //        LinearLayout linearLayout = viewHolder.tl_2.getmTabsContainer();
        //        gainsTabView = new GainsTabView(viewHolder.rootView.getContext());
        //        gainsTabView.setDefault(viewHolder.tl_2.getmTabsContainer().getChildAt(2));
        //        linearLayout.removeViewAt(2);
        //        linearLayout.addView(gainsTabView, 2);
        //        gainsTabView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                viewHolder.tl_2.setCurrentTab(2);
        //                gainsTabView.onClick();
        //            }
        //        });
        //        gainsTabView.setOnChange(new GainsTabView.OnChange() {
        //            @Override
        //            public void onChange(boolean isTop) {
        //                // 涨幅 选择 上涨 还是下跌
        //            }
        //        });
    }

    private void initBarClick() {
        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索
                gotoActivity(SearchCoinMarketActivity.class).startAct();
            }
        });
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //钟
        gotoActivity(SortingUserCoinActivity.class).startAct();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //保存行情列表
                List<ExchangeName> exchangeNames = GsonUtil.getInstance().toList(data, ExchangeName.class);
                if (exchangeNameList == null) {
                    CacheUtils.getInstance().put(CACHE_EXCHANGENAME, data, 60 * 60 * 24);
                    initTablelayout(exchangeNames);
                    break;
                }
                if (exchangeNameList.size() != exchangeNames.size()) {
                    CacheUtils.getInstance().put(CACHE_EXCHANGENAME, data, 60 * 60 * 24);
                    initTablelayout(exchangeNames);
                }
                break;
        }
    }

}
