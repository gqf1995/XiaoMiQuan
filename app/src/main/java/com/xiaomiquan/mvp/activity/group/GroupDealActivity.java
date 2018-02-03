package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.GroupItem;
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

public class GroupDealActivity extends BaseDataBindActivity<GroupDealDelegate, GroupDealBinder> implements CurrencyFragment.OnSelectLinsener {
    CurrencyFragment currencyFragmentBuy;
    CurrencyFragment currencyFragmentSell;
    CoinDetail coinDetail;

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
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle("组合交易"));
        initTablelayout();
        initCurrency();
        addRequest(binder.getBalance(groupItem.getId(), this));
        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void commit() {
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_sell_price.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_input_price));
            return;
        }
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_sell_num.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_input_num));
            return;
        }
        if (coinDetail == null) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_no_select_coin));
            return;
        }
        addRequest(binder.deal(groupItem.getId(),
                viewDelegate.viewHolder.tl_1.getCurrentTab() == 0 ? "1" : "2",
                coinDetail.getCoinId(),
                viewDelegate.viewHolder.et_sell_price.getText().toString(),
                viewDelegate.selectType + "",
                viewDelegate.viewHolder.et_sell_num.getText().toString(), this));
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
        fragments.add(NotDealFragment.newInstance(groupItem.getId()));
        fragments.add(HistoryEntrustFragment.newInstance(groupItem.getId()));
        fragments.add(HistoryTradingFragment.newInstance(groupItem.getId()));
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
        InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, mTitles.toArray(new String[mTitles.size()]));
        viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.vp_sliding);
        viewDelegate.viewHolder.tl_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.changeType(position == 0);
                viewDelegate.showFragment(position);
                if (position == 0) {
                    currencyFragmentBuy.getSelectPositionData();
                } else {
                    currencyFragmentSell.getSelectPositionData();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public static void startAct(Activity activity,
                                GroupItem groupItem,
                                boolean isMy
    ) {
        Intent intent = new Intent(activity, GroupDealActivity.class);
        intent.putExtra("groupItem", groupItem);
        intent.putExtra("isMy", isMy);
        activity.startActivity(intent);
    }

    private GroupItem groupItem;
    boolean isMy;

    private void getIntentData() {
        Intent intent = getIntent();
        groupItem = intent.getParcelableExtra("groupItem");
        isMy = intent.getBooleanExtra("isMy", false);
    }


    public void initCurrency() {
        currencyFragmentBuy = CurrencyFragment.newInstance(CurrencyFragment.TYPE_CURRENCY_BUY, "");
        currencyFragmentSell = CurrencyFragment.newInstance(CurrencyFragment.TYPE_CURRENCY_SELL, groupItem.getId());
        viewDelegate.initAddFragment(R.id.fl_currency, getSupportFragmentManager());
        viewDelegate.addFragment(currencyFragmentBuy);
        viewDelegate.addFragment(currencyFragmentSell);
        viewDelegate.showFragment(0);
    }

    @Override
    public void onSelectLinsener(CoinDetail coinDetail) {
        this.coinDetail = coinDetail;
        viewDelegate.onSelectLinsener(coinDetail, groupItem);
    }
}
