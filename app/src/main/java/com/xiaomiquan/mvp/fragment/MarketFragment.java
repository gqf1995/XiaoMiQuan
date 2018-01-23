package com.xiaomiquan.mvp.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.activity.market.SearchCoinMarketActivity;
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.xiaomiquan.base.AppConst.CACHE_EXCHANGENAME;

/**
 *
 */
public class MarketFragment extends BaseDataBindFragment<TabViewpageDelegate, TabViewpageBinder> {
    ArrayList<Fragment> fragments;
    List<String> mTitles;
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
        initToolbar(new ToolbarBuilder().setmRightImg2(CommonUtils.getString(R.string.ic_Filter2)).setmRightImg1(CommonUtils.getString(R.string.ic_Share))
                .setTitle(CommonUtils.getString(R.string.str_title_market)).setShowBack(true));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        viewDelegate.setBackIconFontText(CommonUtils.getString(R.string.ic_Notifications));
        initBarClick();
        initToolBarSearch();
        //网络获取交易所 名称
        String exchangeNamesStr = CacheUtils.getInstance().getString(CACHE_EXCHANGENAME);
        if (!TextUtils.isEmpty(exchangeNamesStr)) {
            exchangeNameList = GsonUtil.getInstance().toList(exchangeNamesStr, ExchangeName.class);
            initTablelayout(exchangeNameList);
        }
        addRequest(binder.getAllEXchange(this));
    }


    //给toolbar添加搜索布局
    private void initToolBarSearch() {
        viewDelegate.getFl_content().addView(getActivity().getLayoutInflater().inflate(R.layout.layout_top_search, null));
        EditText et_search = viewDelegate.getFl_content().findViewById(R.id.et_search);
        et_search.setFocusable(false);
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转搜索
                gotoActivity(SearchCoinMarketActivity.class).startAct();
                getActivity().overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            }
        });
    }

    @Override
    protected void clickRightIv1() {
        super.clickRightIv();
        // 排序
        gotoActivity(SortingUserCoinActivity.class).fragStartActResult(this, 0x123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                //排序 页面 操作后更新自选
                if (userChooseFragment != null) {
                    userChooseFragment.refreshData();
                }
            }
        }
    }

    @Override
    protected void clickRightIv() {
        super.clickRightIv1();
        // 分享

    }

    UserChooseFragment userChooseFragment;
    ComprehensiveFragment comprehensiveFragment;
    MarketValueFragment marketValueFragment;
    ExchangeNameListFragment exchangeNameListFragment;

    private void initTablelayout(List<ExchangeName> exchangeNames) {

        exchangeNameList = exchangeNames;
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        List<String> strings = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_market));
        userChooseFragment = new UserChooseFragment();
        comprehensiveFragment = new ComprehensiveFragment();
        marketValueFragment = new MarketValueFragment();
        exchangeNameListFragment = ExchangeNameListFragment.newInstance((ArrayList<ExchangeName>) exchangeNames);

        fragments.add(userChooseFragment);
        fragments.add(comprehensiveFragment);
        fragments.add(marketValueFragment);
        fragments.add(exchangeNameListFragment);
        for (int i = 4; i < 8; i++) {
            fragments.add(CoinExchangeFragment.newInstance(strings.get(i)));
        }


        mTitles.add(strings.get(0));
        mTitles.add(strings.get(1));
        mTitles.add(strings.get(2));
        mTitles.add(strings.get(3));
        mTitles.add(strings.get(4));
        mTitles.add(strings.get(5));
        mTitles.add(strings.get(6));
        mTitles.add(strings.get(7));

        for (int i = 0; i < exchangeNames.size(); i++) {
            mTitles.add(exchangeNames.get(i).getEname());
            fragments.add(ExchangeFragment.newInstance(exchangeNames.get(i)));
        }

        viewDelegate.viewHolder.vp_sliding.setOffscreenPageLimit(1);
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles.toArray(new String[mTitles.size()]), (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);

        exchangeNameListFragment.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                //点击交易所列表,跳转相应的tab
                ExchangeName exchangeName = (ExchangeName) item;
                for (int i = 0; i < exchangeNameList.size(); i++) {
                    if (exchangeNameList.get(i).getEname().equals(exchangeName.getEname())) {
                        viewDelegate.viewHolder.tl_2.setCurrentTab(i + 4);
                        break;
                    }
                }
            }
        });
    }

    private void initBarClick() {
        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通知

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
        switch (requestCode) {
            case 0x123:
                //保存行情列表
                List<ExchangeName> exchangeNames = GsonUtil.getInstance().toList(data, ExchangeName.class);
                if (exchangeNameList == null) {
                    CacheUtils.getInstance().put(CACHE_EXCHANGENAME, data, 60 * 60 * 24);
                    initTablelayout(exchangeNames);
                } else {
                    if (exchangeNames.size() != exchangeNameList.size()) {
                        CacheUtils.getInstance().put(CACHE_EXCHANGENAME, data, 60 * 60 * 24);
                        //initTablelayout(exchangeNames);
                    }
                }
                break;
        }
    }

}
