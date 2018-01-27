package com.xiaomiquan.mvp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.DropChangeSort;
import com.xiaomiquan.entity.RiseChangeSort;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.activity.user.ChangeDefaultSetActivity;
import com.xiaomiquan.mvp.databinder.ExchangeBinder;
import com.xiaomiquan.mvp.delegate.ExchangeDelegate;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 交易所 列表页面
 */
public class ExchangeFragment extends BaseDataBindFragment<ExchangeDelegate, ExchangeBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    ExchangeName exchangeName;
    List<ExchangeData> strDatas;
    List<ExchangeData> riseDatas;
    List<ExchangeData> dropDatas;
    List<String> sendKeys;
    List<String> unitList;
    int gainsState = 0;
    final int whatIndex = 1024;

    String onlyKeys = "";

    private ConcurrentLinkedQueue<ExchangeData> exchangeDataList;

    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case whatIndex:
                    if (exchangeDataMap == null) {
                        return;
                    }
                    if (viewDelegate.viewHolder.recycler_view.getScrollState() != 0) {
                        //recycleView正在滑动
                    } else {
                        //更新数据
                        Iterator iter = exchangeDataMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            if (viewDelegate.viewHolder.recycler_view.getScrollState() != 0) {
                                handler.sendEmptyMessageDelayed(whatIndex, 1000);
                                return;
                            }
                            Map.Entry entry = (Map.Entry) iter.next();
                            ExchangeData val = (ExchangeData) entry.getValue();
                            if (val != null) {
                                updataNew(val);
                            } else {
                                break;
                            }
                        }
                        exchangeDataMap.clear();
                    }
                    handler.sendEmptyMessageDelayed(whatIndex, 1000);
                    break;
            }
        }
    };

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
        exchangeName = getArguments().getParcelable("exchangeName");
    }


    private void initList(List<ExchangeData> strDatas) {
        if (exchangeMarketAdapter == null) {
            exchangeMarketAdapter = new ExchangeMarketAdapter(getActivity(), strDatas);
            exchangeMarketAdapter.setDefaultUnit(UserSet.getinstance().getUnit());
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
            viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
            viewDelegate.viewHolder.recycler_view.setAdapter(exchangeMarketAdapter);
            initTool();
        } else {
            exchangeMarketAdapter.setDatas(strDatas);
        }
    }

    //切换单位显示 切换涨跌幅排行
    private void initTool() {
        unitList = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        viewDelegate.viewHolder.tv_unit.setText(UserSet.getinstance().getShowUnit());
        viewDelegate.viewHolder.tv_rise.setText(CommonUtils.getString(R.string.str_rise));
        viewDelegate.viewHolder.tv_rise.setTextColor(CommonUtils.getColor(R.color.color_font2));
        viewDelegate.viewHolder.tv_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDelegate.viewHolder.tv_rise.onClick();
            }
        });
        viewDelegate.viewHolder.tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDefaultSetActivity.startAct(getActivity(), ChangeDefaultSetActivity.TYPE_UNIT);
            }
        });
        viewDelegate.viewHolder.tv_rise.setOnChange(new GainsTabView.OnChange() {
            @Override
            public void onChange(int isTop) {
                gainsState = isTop;
                if (isTop == 0) {
                    exchangeMarketAdapter.setDatas(strDatas);
                } else if (isTop == 1) {
                    exchangeMarketAdapter.setDatas(riseDatas);
                } else if (isTop == 2) {
                    exchangeMarketAdapter.setDatas(dropDatas);
                }
            }
        });
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ExchangeFragment.this.onRefresh();
            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                if (datas.size() > 0) {
                    onlyKeys = datas.get(0).getOnlyKey();
                }
                initList(datas);
                strDatas.addAll(datas);
                initRise();
                initDrop();
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
    public void onResume() {
        super.onResume();
        //同步用户所选 单位
        if (exchangeMarketAdapter != null) {
            viewDelegate.viewHolder.tv_unit.setText(UserSet.getinstance().getShowUnit());
            if (exchangeMarketAdapter.getDatas().size() > 0) {
                exchangeMarketAdapter.setDefaultUnit(UserSet.getinstance().getUnit().replaceAll("-", "\n"));
            }
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        strDatas = Collections.synchronizedList(new ArrayList());
        initList(new ArrayList<ExchangeData>());
        WebSocketRequest.getInstance().addCallBack(exchangeName.getEname(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {
                if (exchangeName.getEname().equals(name)) {
                    //推送数据
                    ExchangeData exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                    if (!TextUtils.isEmpty(exchangeData.getOnlyKey())) {
                        if (exchangeData.getOnlyKey().equals(onlyKeys)) {
                            if (exchangeDataMap == null) {
                                exchangeDataMap = new ConcurrentHashMap<>();
                                handler.sendEmptyMessageDelayed(whatIndex, 1000);
                            }
                            exchangeDataMap.put(exchangeData.getOnlyKey(), exchangeData);
                        }
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
        if (strDatas == null || riseDatas == null || dropDatas == null) {
            return;
        }
        for (int i = 0; i < strDatas.size(); i++) {
            if (strDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                strDatas.set(i, data);
                if (gainsState == 0) {
                    updataPosition = i;
                }
                break;
            }
        }
        for (int i = 0; i < riseDatas.size(); i++) {
            if (riseDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                riseDatas.set(i, data);
                if (gainsState == 1) {
                    updataPosition = i;
                }
                break;
            }
        }
        for (int i = 0; i < dropDatas.size(); i++) {
            if (dropDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                dropDatas.set(i, data);
                if (gainsState == 2) {
                    updataPosition = i;
                }
                break;
            }
        }
        exchangeMarketAdapter.updataOne(updataPosition, data);
    }

    private void sendWebSocket() {
        if (exchangeMarketAdapter != null) {
            if (sendKeys == null) {
                sendKeys = new ArrayList<>();
            } else {
                sendKeys.clear();
            }
            for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
                sendKeys.add(exchangeMarketAdapter.getDatas().get(i).getOnlyKey());
            }
            WebSocketRequest.getInstance().sendData(sendKeys);
        }
    }

    @Override
    public void onDestroy() {
        WebSocketRequest.getInstance().remoceCallBack(exchangeName.getEname());
        super.onDestroy();
    }

    protected void onRefresh() {
        addRequest(binder.getAllMarketByExchange(exchangeName.getEname(), this));
    }

    public static ExchangeFragment newInstance(
            ExchangeName exchangeName) {
        ExchangeFragment newFragment = new ExchangeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("exchangeName", exchangeName);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("exchangeName")) {
            exchangeName = savedInstanceState.getParcelable("exchangeName");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("exchangeName", exchangeName);
    }

    private void initRise() {
        if (riseDatas == null) {
            riseDatas = Collections.synchronizedList(new ArrayList());
        } else {
            riseDatas.clear();
        }
        RiseChangeSort comparator = new RiseChangeSort();
        riseDatas.addAll(strDatas);
        Collections.sort(riseDatas, comparator);
    }

    private void initDrop() {
        if (dropDatas == null) {
            dropDatas = Collections.synchronizedList(new ArrayList());
        } else {
            dropDatas.clear();
        }
        DropChangeSort comparator = new DropChangeSort();
        dropDatas.addAll(strDatas);
        Collections.sort(dropDatas, comparator);
    }
}

