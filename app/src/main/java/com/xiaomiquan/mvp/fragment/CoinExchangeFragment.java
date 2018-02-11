package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.CoinExchangeAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.activity.user.ChangeDefaultSetActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.utils.HandlerHelper;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import skin.support.widget.SkinCompatLinearLayout;

public class CoinExchangeFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    CoinExchangeAdapter exchangeMarketAdapter;
    String coinName;
    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;
    final int whatIndex = 1026;
    List<String> sendKeys;

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        coinName = getArguments().getString("coinName");
    }

    private void initList(List<ExchangeData> strDatas) {
        if (exchangeMarketAdapter == null) {
            exchangeMarketAdapter = new CoinExchangeAdapter(getActivity(), strDatas);
            exchangeMarketAdapter.setDefaultUnit(UserSet.getinstance().getUnit());
            exchangeMarketAdapter.setFirst(true);
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
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            //viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
            //viewDelegate.viewHolder.recycler_view.setAdapter(exchangeMarketAdapter);
            initRecycleViewPull(exchangeMarketAdapter, new LinearLayoutManager(getActivity()));
            initTool();
            viewDelegate.setDefaultPage(0);
        } else {
            //exchangeMarketAdapter.setDatas(strDatas);
            exchangeMarketAdapter.setFirst(true);
            getDataBack(exchangeMarketAdapter.getDatas(), strDatas, exchangeMarketAdapter);
        }
        exchangeMarketAdapter.setFirst(false);
    }

    public TextView tv_unit;
    public GainsTabView tv_rise;
    public SkinCompatLinearLayout lin_root;

    private void initTool() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_exchange_tool, null);
        this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
        this.tv_rise = (GainsTabView) rootView.findViewById(R.id.tv_rise);
        this.lin_root = (SkinCompatLinearLayout) rootView.findViewById(R.id.lin_root);
        List<String> dataset1 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        tv_unit.setText(UserSet.getinstance().getShowUnit());
        tv_unit.setPadding(0, 0, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_40px), 0);
        tv_rise.setText(CommonUtils.getString(R.string.str_rise_24h));
        tv_rise.setTextColor(CommonUtils.getColor(R.color.color_font2));
        tv_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_rise.onClick();
            }
        });
        tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDefaultSetActivity.startAct(getActivity(), ChangeDefaultSetActivity.TYPE_UNIT);
            }
        });
        tv_rise.setVisibility(View.GONE);
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                initList(datas);
                if (datas != null) {
                    //发送 更新请求
                    if (datas.size() > 0) {
                        sendWebSocket();
                    }
                }
                break;
        }
    }

    boolean isVisible;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        this.isVisible = isVisible;
        if (isVisible) {
            onRefresh();
            if (exchangeMarketAdapter != null) {
                tv_unit.setText(UserSet.getinstance().getShowUnit());
                if (exchangeMarketAdapter.getDatas().size() > 0) {
                    exchangeMarketAdapter.notifyDataSetChanged();
                }
            }
        } else {
            binder.cancelpost();
        }
    }


    @Override
    protected void onFragmentFirstVisible() {
        initList(new ArrayList<ExchangeData>());
    }

    //新数据推送 更新
    private void updataNew(ExchangeData data) {
        int updataPosition = 0;
        for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
            if (exchangeMarketAdapter.getDatas().get(i).getOnlyKey().equals(data.getOnlyKey())) {
                updataPosition = i;
                break;
            }
        }
        exchangeMarketAdapter.updataOne(updataPosition, data);
    }

    public void sendWebSocket() {
        if (exchangeMarketAdapter != null) {
            if (sendKeys == null) {
                sendKeys = new ArrayList<>();
            } else {
                sendKeys.clear();
            }
            for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
                sendKeys.add(exchangeMarketAdapter.getDatas().get(i).getOnlyKey());
            }
            initWebSocketRequest();
            WebSocketRequest.getInstance().sendData(sendKeys);
        }
    }

    private void initWebSocketRequest() {
        if (exchangeDataMap == null) {
            exchangeDataMap = new ConcurrentHashMap<>();
            //handler.sendEmptyMessageDelayed(whatIndex, 1000);
        }
        if (viewDelegate != null) {
            HandlerHelper.getinstance().initHander(coinName, exchangeDataMap, viewDelegate.getPullRecyclerView(), new HandlerHelper.OnUpdataLinsener() {
                @Override
                public void onUpdataLinsener(ExchangeData val) {
                    updataNew(val);
                }
            });
            WebSocketRequest.getInstance().addCallBack(coinName, new WebSocketRequest.WebSocketCallBack() {
                @Override
                public void onDataSuccess(String name, String data, String info, int status) {
                    if (coinName.equals(name)) {
                        //推送数据
                        ExchangeData exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                        if (!TextUtils.isEmpty(exchangeData.getOnlyKey())) {
                            HandlerHelper.getinstance().put(exchangeData.getOnlyKey(), exchangeData);
                            //exchangeDataMap.put(exchangeData.getOnlyKey(), exchangeData);
                        }
                    }
                }

                @Override
                public void onDataError(String name, String data, String info, int status) {

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        WebSocketRequest.getInstance().remoceCallBack(coinName);
        super.onDestroy();
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getAllMarketBySymbol(coinName, this));
    }
    //    protected void onRefresh() {
    //        addRequest(binder.getAllMarketBySymbol(coinName, this));
    //    }

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
