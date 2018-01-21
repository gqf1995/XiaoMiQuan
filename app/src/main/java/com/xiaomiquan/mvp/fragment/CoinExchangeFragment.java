package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.databinder.ExchangeBinder;
import com.xiaomiquan.mvp.delegate.ExchangeDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoinExchangeFragment extends BaseDataBindFragment<ExchangeDelegate, ExchangeBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    String coinName;
    List<ExchangeData> strDatas;

    @Override
    protected Class<ExchangeDelegate> getDelegateClass() {
        return ExchangeDelegate.class;
    }

    @Override
    public ExchangeBinder getDataBinder(ExchangeDelegate viewDelegate) {
        return new ExchangeBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        coinName = getArguments().getString("coinName");
    }


    private void initList(List<ExchangeData> strDatas) {
        if (exchangeMarketAdapter == null) {
            exchangeMarketAdapter = new ExchangeMarketAdapter(getActivity(), strDatas);
            exchangeMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    MarketDetailsActivity.startAct(getActivity(), exchangeMarketAdapter.getDatas().get(position));
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.recycler_view.setAdapter(exchangeMarketAdapter);
            initTool();
        } else {
            exchangeMarketAdapter.setDatas(strDatas);
        }
    }

    private void initTool() {
        List<String> dataset1 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        viewDelegate.viewHolder.tv_unit.attachDataSource(dataset1);
        viewDelegate.viewHolder.tv_rise.setText(CommonUtils.getString(R.string.str_rise));
        viewDelegate.viewHolder.tv_rise.setTextColor(CommonUtils.getColor(R.color.color_font2));
        viewDelegate.viewHolder.tv_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDelegate.viewHolder.tv_rise.onClick();
            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                initList(datas);
                strDatas = datas;
                sendWebSocket();
                break;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }


    @Override
    protected void onFragmentFirstVisible() {
        strDatas = new ArrayList<>();
        initList(strDatas);
        WebSocketRequest.getInstance().addCallBack(coinName, new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name,String data, String info, int status) {

            }

            @Override
            public void onDataError(String name,String data, String info, int status) {

            }
        });
    }

    private void sendWebSocket() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < strDatas.size(); i++) {
            datas.add(strDatas.get(i).getOnlyKey());
        }
        WebSocketRequest.getInstance().sendData(datas);
    }

    @Override
    public void onResume() {
        super.onResume();
        //重新发送
    }

    @Override
    public void onDestroy() {
        WebSocketRequest.getInstance().remoceCallBack(coinName);
        super.onDestroy();
    }

    protected void onRefresh() {
        addRequest(binder.getAllMarketBySymbol(coinName, this));
    }

    public static CoinExchangeFragment newInstance(
            String coinName) {
        CoinExchangeFragment newFragment = new CoinExchangeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("coinName", coinName);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("coinName")) {
            coinName = savedInstanceState.getString("coinName");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("coinName", coinName);
    }

}
