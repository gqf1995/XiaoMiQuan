package com.xiaomiquan.mvp.activity.market;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.CoinMarketBinder;
import com.xiaomiquan.mvp.delegate.CoinMarketDelegate;
import com.xiaomiquan.mvp.fragment.CoinExchangeFragment;

public class CoinMarketActivity extends BaseDataBindActivity<CoinMarketDelegate, CoinMarketBinder> {

    @Override
    protected Class<CoinMarketDelegate> getDelegateClass() {
        return CoinMarketDelegate.class;
    }

    @Override
    public CoinMarketBinder getDataBinder(CoinMarketDelegate viewDelegate) {
        return new CoinMarketBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(type));
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        viewDelegate.addFragment(CoinExchangeFragment.newInstance(type));
        viewDelegate.showFragment(0);
    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, CoinMarketActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
