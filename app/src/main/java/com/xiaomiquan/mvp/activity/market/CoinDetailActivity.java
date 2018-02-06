package com.xiaomiquan.mvp.activity.market;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.CoinExchangeAdapter;
import com.xiaomiquan.entity.bean.CoinData;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.databinder.CoinDetailBinder;
import com.xiaomiquan.mvp.delegate.CoinDetailDelegate;

import java.util.List;

public class CoinDetailActivity extends BaseDataBindActivity<CoinDetailDelegate, CoinDetailBinder> {

    CoinExchangeAdapter exchangeMarketAdapter;

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
        addRequest(binder.getAllMarketBySymbol(exchangeData.getSymbol(), this));
        addRequest(binder.getSymbolInfomation(exchangeData.getSymbol(), this));
        viewDelegate.viewHolder.tv_look_more_global_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoinMarketActivity.startAct(CoinDetailActivity.this, exchangeData.getSymbol());
            }
        });
    }

    public static void startAct(Activity activity,
                                ExchangeData exchangeData) {
        Intent intent = new Intent(activity, CoinDetailActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
    }

    ExchangeData exchangeData;
    CoinData coinData;

    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
    }

    private void initList(List<ExchangeData> data) {
        exchangeMarketAdapter = new CoinExchangeAdapter(this, data);
        viewDelegate.viewHolder.rv_global_market.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_global_market.setAdapter(exchangeMarketAdapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //根据币种名称获得相关信息
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                if (datas.size() > 5) {
                    initList(datas.subList(datas.size() - 5, datas.size()));
                    return;
                }
                initList(datas);
                break;
            case 0x124:
                //币种资料
                CoinData coin = GsonUtil.getInstance().toObj(data, CoinData.class);
                addRequest(binder.getMarketCapById(coin.getNameEg(), this));
                break;
            case 0x125:
                ExchangeData exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                viewDelegate.initData(coinData, exchangeData);

                break;
        }
    }

}
