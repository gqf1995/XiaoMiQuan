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
import com.xiaomiquan.entity.RiseChangeSort;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.databinder.ExchangeBinder;
import com.xiaomiquan.mvp.delegate.ExchangeDelegate;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        viewDelegate.viewHolder.tv_rise.setOnChange(new GainsTabView.OnChange() {
            @Override
            public void onChange(int isTop) {
                if (isTop == 0) {
                    exchangeMarketAdapter.setDatas(strDatas);
                } else if (isTop == 1) {
                    exchangeMarketAdapter.setDatas(riseDatas);
                } else if (isTop == 2) {
                    exchangeMarketAdapter.setDatas(dropDatas);
                }
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
    protected void onFragmentFirstVisible() {
        strDatas = new ArrayList<>();
        initList(strDatas);
        WebSocketRequest.getInstance().addCallBack(exchangeName.getEname(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {
                if (exchangeName.getEname().equals(name)) {
                    //推送数据

                }
            }

            @Override
            public void onDataError(String name, String data, String info, int status) {

            }
        });
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
    public void onResume() {
        super.onResume();
        //重新发送
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
        RiseChangeSort comparator = new RiseChangeSort();
        dropDatas.addAll(strDatas);
        Collections.sort(dropDatas, comparator);
    }
}

