package com.xiaomiquan.mvp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.market.SearchCoinMarketActivity;
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.widget.GainsTabView;

import java.util.ArrayList;

public class MarketFragment extends BaseDataBindFragment<TabViewpageDelegate, TabViewpageBinder> {
    ArrayList<Fragment> fragments;
    String[] mTitles;
    GainsTabView gainsTabView;

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
        initToolbar(new ToolbarBuilder().setSubTitle(CommonUtils.getString(R.string.ic_zhankai)).setTitle(CommonUtils.getString(R.string.str_title_market)).setShowBack(true));
        viewDelegate.setBackIconFontText(CommonUtils.getString(R.string.ic_zhankai));
        initBarClick();
        initTablelayout();

    }

    private void initTablelayout() {
        if (mTitles == null && fragments == null) {
            fragments = new ArrayList<>();
            mTitles = CommonUtils.getStringArray(R.array.sa_select_market);
        }

        fragments.add(new UserChooseFragment());
        fragments.add(new MarketValueFragment());

        for (int i = 2; i < mTitles.length; i++) {
            fragments.add(new InstitutionsFragment());
        }
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles, (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);

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
        }
    }

}
