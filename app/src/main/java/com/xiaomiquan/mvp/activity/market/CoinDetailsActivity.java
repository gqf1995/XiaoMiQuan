package com.xiaomiquan.mvp.activity.market;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.delegate.CoinMarketDelegate;
import com.xiaomiquan.mvp.fragment.CoinDetailFragment;

/**
 * Created by 郭青枫 on 2018/2/11 0011.
 */

public class CoinDetailsActivity extends BaseActivity<CoinMarketDelegate> {
    @Override
    protected Class<CoinMarketDelegate> getDelegateClass() {
        return CoinMarketDelegate.class;
    }

    CoinDetailFragment coinDetailFragment;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(exchangeData.getSymbol()));
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        coinDetailFragment = new CoinDetailFragment();
        coinDetailFragment.setExchangeData(exchangeData);
        viewDelegate.addFragment(coinDetailFragment);
        viewDelegate.showFragment(0);
    }

    ExchangeData exchangeData;

    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
    }

    public static void startAct(Activity activity,
                                ExchangeData exchangeData) {
        Intent intent = new Intent(activity, CoinDetailsActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
    }
}
