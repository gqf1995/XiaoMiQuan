package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import skin.support.widget.SkinCompatLinearLayout;

public class CoinExchangeFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    CoinExchangeAdapter exchangeMarketAdapter;
    String coinName;
    List<ExchangeData> strDatas;
    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;
    final int whatIndex = 1026;
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case whatIndex:
                    if (isVisible) {
                        if (exchangeDataMap == null) {
                            return;
                        }
                        if (viewDelegate.viewHolder.pull_recycleview.getScrollState() != 0) {
                            //recycleView正在滑动
                        } else {
                            //更新数据
                            Iterator iter = exchangeDataMap.entrySet().iterator();
                            while (iter.hasNext()) {
                                if (viewDelegate.viewHolder.pull_recycleview.getScrollState() != 0) {
                                    handler.sendEmptyMessageDelayed(whatIndex, 1000);
                                    return;
                                }
                                Map.Entry entry = (Map.Entry) iter.next();
                                ExchangeData val = (ExchangeData) entry.getValue();
                                String key = (String) entry.getKey();
                                if (val != null) {
                                    updataNew(val);
                                    exchangeDataMap.remove(key);
                                } else {
                                }
                            }
                        }
                    }
                    handler.sendEmptyMessageDelayed(whatIndex, 1000);
                    break;
            }
        }
    };

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
        } else {
            //exchangeMarketAdapter.setDatas(strDatas);
            getDataBack(exchangeMarketAdapter.getDatas(), strDatas, exchangeMarketAdapter);
        }
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
        tv_rise.setText(CommonUtils.getString(R.string.str_rise));
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
                strDatas = datas;
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

        strDatas = new ArrayList<>();
        initList(strDatas);
        WebSocketRequest.getInstance().addCallBack(coinName, new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {
                if (coinName.equals(name)) {
                    //推送数据
                    ExchangeData exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                    if (!TextUtils.isEmpty(exchangeData.getOnlyKey())) {
                        if (exchangeDataMap == null) {
                            exchangeDataMap = new ConcurrentHashMap<>();
                            handler.sendEmptyMessageDelayed(whatIndex, 1000);
                        }
                        exchangeDataMap.put(exchangeData.getOnlyKey(), exchangeData);
                    }
                }
            }

            @Override
            public void onDataError(String name, String data, String info, int status) {

            }
        });
    }

    //新数据推送 更新
    private void updataNew(ExchangeData data) {
        int updataPosition = 0;
        for (int i = 0; i < strDatas.size(); i++) {
            if (strDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                strDatas.set(i, data);
                updataPosition = i;
                break;
            }
        }
        exchangeMarketAdapter.updataOne(updataPosition, data);
    }

    private void sendWebSocket() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < strDatas.size(); i++) {
            datas.add(strDatas.get(i).getOnlyKey());
        }
        WebSocketRequest.getInstance().sendData(datas);
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
