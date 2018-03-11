package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.market.SearchCoinMarketActivity;
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.utils.UiHeplUtils;

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
    UserLogin userLogin;

    int defaultLenght;

    public interface OnHttpChangeLinsener {
        void initSocket();
    }

    OnHttpChangeLinsener onHttpChangeLinsener;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        onHttpChangeLinsener = (OnHttpChangeLinsener) context;
    }

    @Override
    protected Class<TabViewpageDelegate> getDelegateClass() {
        return TabViewpageDelegate.class;
    }

    @Override
    public TabViewpageBinder getDataBinder(TabViewpageDelegate viewDelegate) {
        return new TabViewpageBinder(viewDelegate);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (viewDelegate != null) {
            if (!ListUtils.isEmpty(viewDelegate.getFragmentList())) {
                viewDelegate.showFragment(viewDelegate.viewHolder.tl_2.getCurrentTab());
            }
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        initToolbar(new ToolbarBuilder().setmRightImg1(CommonUtils.getString(R.string.ic_Share))
                .setTitle(CommonUtils.getString(R.string.str_title_market)).setShowBack(true));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        viewDelegate.setBackIconFontText(CommonUtils.getString(R.string.ic_Filter2));
        viewDelegate.setToolColor(R.color.mark_color, false);
        initBarClick();
        initToolBarSearch();
        //网络获取交易所 名称
        String exchangeNamesStr = CacheUtils.getInstance().getString(CACHE_EXCHANGENAME);
        if (!TextUtils.isEmpty(exchangeNamesStr)) {
            exchangeNameList = GsonUtil.getInstance().toList(exchangeNamesStr, ExchangeName.class);
        } else {
            String datas = CommonUtils.getString(R.string.str_eNames);
            CacheUtils.getInstance().put(CACHE_EXCHANGENAME, datas);
            exchangeNameList = GsonUtil.getInstance().toList(datas, ExchangeName.class);
        }
        initTablelayout(exchangeNameList);
    }

    ArrayList<String> strings;

    public void sendWebsocket() {
        if (fragments == null) {
            return;
        }
        if (viewDelegate != null) {
            if (viewDelegate.viewHolder.tl_2 != null) {
                for (int i = 0; i < fragments.size(); i++) {
                    if (i == viewDelegate.viewHolder.tl_2.getCurrentTab()) {
                        if (fragments.get(i) instanceof ExchangeFragment) {
                            ((ExchangeFragment) fragments.get(i)).sendWebSocket();
                        } else if (fragments.get(i) instanceof MarketValueFragment) {
                            ((MarketValueFragment) fragments.get(i)).sendWebSocket();
                        } else if (fragments.get(i) instanceof UserChooseFragment) {
                            ((UserChooseFragment) fragments.get(i)).sendWebSocket();
                        } else if (fragments.get(i) instanceof CoinExchangeFragment) {
                            ((CoinExchangeFragment) fragments.get(i)).sendWebSocket();
                        }
                    }
                }
            }
        }
    }


    //给toolbar添加搜索布局
    private void initToolBarSearch() {
        viewDelegate.getFl_content().addView(getActivity().getLayoutInflater().inflate(R.layout.layout_top_search, null));
        LinearLayout lin_search_root = viewDelegate.getFl_content().findViewById(R.id.lin_search_root);
        EditText et_search = viewDelegate.getFl_content().findViewById(R.id.et_search);
        et_search.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        et_search.setFocusable(false);
        lin_search_root.setGravity(Gravity.CENTER);
        lin_search_root.setPadding(lin_search_root.getLeft(), lin_search_root.getTop(), lin_search_root.getRight() + (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_30px), lin_search_root.getBottom());
        lin_search_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch();
            }
        });
    }

    private void goSearch() {
        if (userChooseFragment != null) {
            if (userChooseFragment.getExchangeMarketAdapter() != null) {
                //跳转搜索
                if (strings == null) {
                    strings = new ArrayList<>();
                } else {
                    strings.clear();
                }
                for (int i = 0; i < userChooseFragment.getExchangeMarketAdapter().getDatas().size(); i++) {
                    strings.add(userChooseFragment.getExchangeMarketAdapter().getDatas().get(i).getOnlyKey());
                }
                SearchCoinMarketActivity.startAct(getActivity(), (ArrayList) strings, 0x123);
                getActivity().overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void clickRightIv1() {
        super.clickRightIv();
        // 排序
        if (SingSettingDBUtil.isLogin(getActivity())) {
            gotoActivity(SortingUserCoinActivity.class).fragStartActResult(this, 0x123);
        }
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
        //        CircleDialogHelper.initDefaultInputDialog(getActivity(), "修改地址", "", "确定", new OnInputClickListener() {
        //            @Override
        //            public void onClick(String text, View v) {
        //                HttpUrl.setBaseUrl(text);
        //                //onHttpChangeLinsener.initSocket();
        //            }
        //        }).setDefaultInputTxt(HttpUrl.getBaseUrl()).show();
        List<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(UiHeplUtils.screenShot(getActivity()));
        UiHeplUtils.shareImgs(getActivity(), bitmaps);
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
        defaultLenght = strings.size();
        userChooseFragment = new UserChooseFragment();
        comprehensiveFragment = new ComprehensiveFragment();
        marketValueFragment = new MarketValueFragment();
        exchangeNameListFragment = ExchangeNameListFragment.newInstance((ArrayList<ExchangeName>) exchangeNames);

        fragments.add(userChooseFragment);
        //fragments.add(comprehensiveFragment);
        fragments.add(marketValueFragment);
        fragments.add(exchangeNameListFragment);
        for (int i = defaultLenght - 4; i < defaultLenght; i++) {
            fragments.add(CoinExchangeFragment.newInstance(strings.get(i)));
        }

        for (int i = 0; i < defaultLenght; i++) {
            mTitles.add(strings.get(i));
        }

        for (int i = 0; i < exchangeNames.size(); i++) {
            mTitles.add(exchangeNames.get(i).getEname());
            fragments.add(ExchangeFragment.newInstance(exchangeNames.get(i)));
        }

        viewDelegate.viewHolder.vp_sliding.setOffscreenPageLimit(1);
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles.toArray(new String[mTitles.size()]), this, fragments);

        exchangeNameListFragment.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                //点击交易所列表,跳转相应的tab
                ExchangeName exchangeName = (ExchangeName) item;
                for (int i = 0; i < exchangeNameList.size(); i++) {
                    if (exchangeNameList.get(i).getEname().equals(exchangeName.getEname())) {
                        viewDelegate.viewHolder.tl_2.setCurrentTab(i + defaultLenght);
                        break;
                    }
                }
            }
        });
        if (userLogin == null) {
            viewDelegate.viewHolder.tl_2.setCurrentTab(1);
            viewDelegate.viewHolder.tl_2.setCurrentTab(1);
        }
    }

    private void initBarClick() {
        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通知
                //new LogDialog().show(getChildFragmentManager(), "");
                // 排序
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    gotoActivity(SortingUserCoinActivity.class).fragStartActResult(MarketFragment.this, 0x123);
                }
            }
        });
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        // 排序
        if (SingSettingDBUtil.isLogin(getActivity())) {
            gotoActivity(SortingUserCoinActivity.class).fragStartActResult(this, 0x123);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //保存行情列表
                //                List<ExchangeName> exchangeNames = GsonUtil.getInstance().toList(data, ExchangeName.class);
                //                if (exchangeNameList == null) {
                //                    CacheUtils.getInstance().put(CACHE_EXCHANGENAME, data, 60 * 60 * 24);
                //                    initTablelayout(exchangeNames);
                //                } else {
                //                    if (exchangeNames.size() != exchangeNameList.size()) {
                //                        CacheUtils.getInstance().put(CACHE_EXCHANGENAME, data, 60 * 60 * 24);
                //                    }
                //                }
                break;
        }
    }

}
