package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.CoinExchangeAdapter;
import com.xiaomiquan.entity.bean.CoinData;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.market.CoinMarketActivity;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.databinder.CoinDetailBinder;
import com.xiaomiquan.mvp.delegate.CoinDetailDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class CoinDetailFragment extends BaseDataBindFragment<CoinDetailDelegate, CoinDetailBinder> {

    CoinExchangeAdapter exchangeMarketAdapter;
    boolean isShowToast = false;

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

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    private void initList(List<ExchangeData> data) {
        exchangeMarketAdapter = new CoinExchangeAdapter(getActivity(), data);
        exchangeMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position > -1) {
                    MarketDetailsActivity.startAct(getActivity(), exchangeMarketAdapter.getDatas().get(position));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
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
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, "market", ExchangeData.class);
                if (datas != null) {
                    initList(datas);
                }
                coinData = GsonUtil.getInstance().toObj(data, "symbolInf", CoinData.class);
                if (coinData != null) {
                    viewDelegate.initCoinData(coinData);
                    if (!TextUtils.isEmpty(coinData.getNameEg())) {
                        addRequest(binder.getMarketCapById(coinData.getNameEg(), this));
                    }
                }
                break;
            case 0x125:
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                ExchangeData exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                viewDelegate.initData(coinData, exchangeData);
                if (isShowToast) {
                    viewDelegate.viewHolder.tv_toast.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

}
