package com.xiaomiquan.mvp.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.view.GridSpacingItemDecoration;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.CoinIndexAdapter;
import com.xiaomiquan.adapter.RankingAdapter;
import com.xiaomiquan.adapter.VolumeAdapter;
import com.xiaomiquan.entity.bean.CoinIndex;
import com.xiaomiquan.mvp.activity.market.CoinIndexActivity;
import com.xiaomiquan.mvp.databinder.ComprehensiveBinder;
import com.xiaomiquan.mvp.delegate.ComprehensiveDelegate;
import java.util.ArrayList;
import java.util.List;

public class ComprehensiveFragment extends BaseDataBindFragment<ComprehensiveDelegate, ComprehensiveBinder> {

    CoinIndexAdapter coinIndexAdapter;
    VolumeAdapter volumeAdapter;
    RankingAdapter rankingAdapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        addRequest(binder.getAllMarketCaps(this));
        initRefush();
        initVolume();
        initRanking();
        viewDelegate.setOnClickListener(this, R.id.lin_coin_index);
    }

    private void initRefush() {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getAllMarketCaps(ComprehensiveFragment.this));
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_coin_index:
                //币种指数
                gotoActivity(CoinIndexActivity.class).startAct();
                break;
        }
    }

    private void initCoinIndex(List<CoinIndex> coinIndices) {
        List<CoinIndex> data = new ArrayList<>();
        data.add(coinIndices.get(0));
        data.add(coinIndices.get(1));
        data.add(coinIndices.get(2));
        if (coinIndexAdapter == null) {
            coinIndexAdapter = new CoinIndexAdapter(getActivity(), data);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            viewDelegate.viewHolder.rv_coin_index.addItemDecoration(new GridSpacingItemDecoration(3, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_20px), true));
            viewDelegate.viewHolder.rv_coin_index.setLayoutManager(gridLayoutManager);
            viewDelegate.viewHolder.rv_coin_index.setAdapter(coinIndexAdapter);
        } else {
            coinIndexAdapter.setDatas(data);
        }
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
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                viewDelegate.viewHolder.lin_root.setVisibility(View.VISIBLE);
                List<CoinIndex> coinIndices = GsonUtil.getInstance().toList(data, CoinIndex.class);
                initCoinIndex(coinIndices);
                break;
        }
    }

    @Override
    protected Class<ComprehensiveDelegate> getDelegateClass() {
        return ComprehensiveDelegate.class;
    }

    @Override
    public ComprehensiveBinder getDataBinder(ComprehensiveDelegate viewDelegate) {
        return new ComprehensiveBinder(viewDelegate);
    }


}
