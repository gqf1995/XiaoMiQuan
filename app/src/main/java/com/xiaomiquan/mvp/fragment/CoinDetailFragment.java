package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.CoinExchangeAdapter;
import com.xiaomiquan.entity.bean.CoinData;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.market.CoinMarketActivity;
import com.xiaomiquan.mvp.databinder.CoinDetailBinder;
import com.xiaomiquan.mvp.delegate.CoinDetailDelegate;

import java.util.List;

public class CoinDetailFragment extends BaseDataBindFragment<CoinDetailDelegate, CoinDetailBinder> {

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
        initToolbar(new ToolbarBuilder().setTitle(exchangeData.getSymbol()));
        addRequest(binder.getSymbolInfomation(exchangeData.getSymbol(), this));
        viewDelegate.viewHolder.tv_look_more_global_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoinMarketActivity.startAct(getActivity(), exchangeData.getSymbol());
            }
        });
    }

    ExchangeData exchangeData;
    CoinData coinData;

    public void setExchangeData(ExchangeData exchangeData) {
        this.exchangeData = exchangeData;
    }

    private void initList(List<ExchangeData> data) {
        exchangeMarketAdapter = new CoinExchangeAdapter(getActivity(), data);
        viewDelegate.viewHolder.rv_global_market.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_global_market.setAdapter(exchangeMarketAdapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x124:
                //币种资料
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, "market", ExchangeData.class);
                if (datas != null) {
                    initList(datas);
                }
                coinData = GsonUtil.getInstance().toObj(data, "symbolInf", CoinData.class);
                if (coinData != null) {
                    if (!TextUtils.isEmpty(coinData.getNameEg())) {
                        addRequest(binder.getMarketCapById(coinData.getNameEg(), this));
                    }
                }
                break;
            case 0x125:
                ExchangeData exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                viewDelegate.initData(coinData, exchangeData);
                break;
        }
    }

}
