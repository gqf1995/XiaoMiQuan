package com.xiaomiquan.mvp.activity.market;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.databinder.CoinDetailBinder;
import com.xiaomiquan.mvp.delegate.CoinDetailDelegate;

public class CoinDetailActivity extends BaseDataBindActivity<CoinDetailDelegate, CoinDetailBinder> {

    @Override
    protected Class<CoinDetailDelegate> getDelegateClass() {
        return CoinDetailDelegate.class;
    }

    @Override
    public CoinDetailBinder getDataBinder(CoinDetailDelegate viewDelegate) {
        return new CoinDetailBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(exchangeData.getSymbol()));
    }

    public static void startAct(Activity activity,
                                ExchangeData exchangeData) {
        Intent intent = new Intent(activity, CoinDetailActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
    }

    ExchangeData exchangeData;

    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
        viewDelegate.initData(exchangeData);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
