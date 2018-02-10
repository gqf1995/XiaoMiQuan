package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.DropChangeSort;
import com.xiaomiquan.entity.RiseChangeSort;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.ExchangeName;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import skin.support.widget.SkinCompatLinearLayout;

/**
 * 交易所 列表页面
 */
public class ExchangeFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    ExchangeName exchangeName;
    List<ExchangeData> strDatas;
    List<ExchangeData> riseDatas;
    List<ExchangeData> dropDatas;
    List<String> sendKeys;
    List<String> unitList;
    final int whatIndex = 1025;
    String onlyKeys;
    int sortingType = 0;
    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;


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
        exchangeName = getArguments().getParcelable("exchangeName");
    }


    private void initList(List<ExchangeData> strDatas) {
        if (exchangeMarketAdapter == null) {
            exchangeMarketAdapter = new ExchangeMarketAdapter(getActivity(), strDatas);
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
            initRecycleViewPull(exchangeMarketAdapter, new LinearLayoutManager(getActivity()));
            initTool();
            viewDelegate.setDefaultPage(0);
        } else {
            exchangeMarketAdapter.setFirst(true);
            getDataBack(exchangeMarketAdapter.getDatas(), strDatas, exchangeMarketAdapter);
        }
        exchangeMarketAdapter.setFirst(false);
    }

    public TextView tv_unit;
    public GainsTabView tv_rise;
    public SkinCompatLinearLayout lin_root;

    //切换单位显示 切换涨跌幅排行
    private void initTool() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_exchange_tool, null);
        this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
        this.tv_rise = (GainsTabView) rootView.findViewById(R.id.tv_rise);
        this.lin_root = (SkinCompatLinearLayout) rootView.findViewById(R.id.lin_root);

        unitList = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        tv_unit.setText(UserSet.getinstance().getShowUnit());
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
        tv_rise.setOnChange(new GainsTabView.OnChange() {
            @Override
            public void onChange(int isTop) {
                sortingType = isTop;
                if (isTop == 0) {
                    exchangeMarketAdapter.setDatas(strDatas);
                } else if (isTop == 1) {
                    initRise();
                    exchangeMarketAdapter.setDatas(riseDatas);
                } else if (isTop == 2) {
                    initDrop();
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
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                initList(datas);
                strDatas.clear();
                strDatas.addAll(exchangeMarketAdapter.getDatas());
                if (strDatas.size() > 0) {
                    //排序
                    if (sortingType == 0) {
                        exchangeMarketAdapter.setDatas(strDatas);
                    } else if (sortingType == 1) {
                        initRise();
                        exchangeMarketAdapter.setDatas(riseDatas);
                    } else if (sortingType == 2) {
                        initDrop();
                        exchangeMarketAdapter.setDatas(dropDatas);
                    }
                }
                if (datas != null) {
                    //发送 更新请求
                    if (datas.size() > 0) {
                        sendWebSocket();
                        onlyKeys = datas.get(0).getOnlyKey();
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
            //同步用户所选 单位
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

    private void initWebSocketRequest() {
        if (exchangeDataMap == null) {
            exchangeDataMap = new ConcurrentHashMap<>();
            //handler.sendEmptyMessageDelayed(whatIndex, 1000);
        }
        if (viewDelegate != null) {
            HandlerHelper.getinstance().initHander(exchangeName.getEname(), exchangeDataMap, viewDelegate.getPullRecyclerView(), new HandlerHelper.OnUpdataLinsener() {
                @Override
                public void onUpdataLinsener(ExchangeData val) {
                    updataNew(val);
                }
            });
            WebSocketRequest.getInstance().addCallBack(exchangeName.getEname(), new WebSocketRequest.WebSocketCallBack() {
                @Override
                public void onDataSuccess(String name, String data, String info, int status) {
                    if (exchangeName.getEname().equals(name)) {
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
    protected void onFragmentFirstVisible() {
        strDatas = new ArrayList<>();
        initList(new ArrayList<ExchangeData>());

    }

    //新数据推送 更新
    private void updataNew(ExchangeData data) {
        int updataPosition = 0;
        if (exchangeMarketAdapter == null) {
            return;
        }
        for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
            if (exchangeMarketAdapter.getDatas().get(i).getOnlyKey().equals(data.getOnlyKey())) {
                updataPosition = i;
                break;
            }
        }
        for (int i = 0; i < strDatas.size(); i++) {
            if (strDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                strDatas.set(i, data);
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

    @Override
    public void onDestroy() {
        WebSocketRequest.getInstance().remoceCallBack(exchangeName.getEname());
        super.onDestroy();
    }

    @Override
    protected void refreshData() {
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

    public void checkRedRise() {
        if (exchangeMarketAdapter != null) {
            exchangeMarketAdapter.checkRedRise(exchangeMarketAdapter);
        }
    }

    private void initRise() {
        if (riseDatas == null) {
            riseDatas = new ArrayList<>();
        } else {
            riseDatas.clear();
        }
        RiseChangeSort comparator = new RiseChangeSort();
        riseDatas.addAll(strDatas);
        Collections.sort(riseDatas, comparator);
    }

    private void initDrop() {
        if (dropDatas == null) {
            dropDatas = new ArrayList<>();
        } else {
            dropDatas.clear();
        }
        DropChangeSort comparator = new DropChangeSort();
        dropDatas.addAll(strDatas);
        Collections.sort(dropDatas, comparator);
    }


}

