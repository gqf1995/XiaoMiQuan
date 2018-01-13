package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.adapter.CoinIndexAdapter;
import com.xiaomiquan.adapter.RankingAdapter;
import com.xiaomiquan.adapter.VolumeAdapter;
import com.xiaomiquan.mvp.databinder.MarketValueBinder;
import com.xiaomiquan.mvp.delegate.MarketValueDelegate;

import java.util.ArrayList;
import java.util.List;

public class MarketValueFragment extends BaseDataBindFragment<MarketValueDelegate, MarketValueBinder> {

    CoinIndexAdapter coinIndexAdapter;
    VolumeAdapter volumeAdapter;
    RankingAdapter rankingAdapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initCoinIndex();
        initVolume();
        initRanking();
    }

    private void initCoinIndex() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("");
        }
        coinIndexAdapter = new CoinIndexAdapter(getActivity(), data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewDelegate.viewHolder.rv_coin_index.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_coin_index.setAdapter(coinIndexAdapter);
    }

    private void initVolume() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("");
        }
        volumeAdapter = new VolumeAdapter(getActivity(), data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.rv_deal_num.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_deal_num.setAdapter(volumeAdapter);
    }

    private void initRanking() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("");
        }
        rankingAdapter = new RankingAdapter(getActivity(), data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.rv_ranking.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_ranking.setAdapter(rankingAdapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected Class<MarketValueDelegate> getDelegateClass() {
        return MarketValueDelegate.class;
    }

    @Override
    public MarketValueBinder getDataBinder(MarketValueDelegate viewDelegate) {
        return new MarketValueBinder(viewDelegate);
    }

}
