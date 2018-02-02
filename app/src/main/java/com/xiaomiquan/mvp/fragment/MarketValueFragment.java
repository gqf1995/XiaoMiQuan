package com.xiaomiquan.mvp.fragment;

import android.os.Handler;
import android.os.Message;
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
import com.xiaomiquan.adapter.CoinMarketAdapter;
import com.xiaomiquan.entity.Drop24ChangeSort;
import com.xiaomiquan.entity.Rise24ChangeSort;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.user.ChangeDefaultSetActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.utils.HandlerHelper;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import skin.support.widget.SkinCompatLinearLayout;

public class MarketValueFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    CoinMarketAdapter exchangeMarketAdapter;
    List<ExchangeData> defaultDatas;
    List<ExchangeData> riseDatas;
    List<ExchangeData> dropDatas;
    List<String> sendKeys;
    final int whatIndex = 1024;
    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case whatIndex:
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
                    handler.sendEmptyMessageDelayed(whatIndex, 1000);
                    break;
            }
        }
    };

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
        for (int i = 0; i < defaultDatas.size(); i++) {
            if (defaultDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                defaultDatas.set(i, data);
                break;
            }
        }
        exchangeMarketAdapter.updataOne(updataPosition, data);
    }

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
    }


    private void initList(List<ExchangeData> strDatas) {
        if (exchangeMarketAdapter == null) {
            exchangeMarketAdapter = new CoinMarketAdapter(getActivity(), strDatas);
            exchangeMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    // MarketDetailsActivity.startAct(getActivity(), exchangeMarketAdapter.getDatas().get(position));
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            initRecycleViewPull(exchangeMarketAdapter, new LinearLayoutManager(getActivity()));
            viewDelegate.setIsLoadMore(false);
            initTool();
        } else {
            getDataBack(exchangeMarketAdapter.getDatas(), strDatas, exchangeMarketAdapter);
        }
    }

    public TextView tv_unit;
    public GainsTabView tv_rise;
    public SkinCompatLinearLayout lin_root;
    int gainsState = 0;

    private void initTool() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_exchange_tool, null);
        this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
        this.tv_rise = (GainsTabView) rootView.findViewById(R.id.tv_rise);
        this.lin_root = (SkinCompatLinearLayout) rootView.findViewById(R.id.lin_root);
        tv_unit.setText(UserSet.getinstance().getUnit());

        tv_rise.setText(CommonUtils.getString(R.string.str_rise));
        tv_rise.setTextColor(CommonUtils.getColor(R.color.color_font2));
        tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDefaultSetActivity.startAct(getActivity(), ChangeDefaultSetActivity.TYPE_UNIT);
            }
        });
        tv_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_rise.onClick();
            }
        });
        tv_rise.setOnChange(new GainsTabView.OnChange() {
            @Override
            public void onChange(int isTop) {
                gainsState = isTop;
                if (isTop == 0) {
                    exchangeMarketAdapter.setDatas(defaultDatas);
                } else if (isTop == 1) {
                    initRise();
                    exchangeMarketAdapter.setDatas(riseDatas);
                } else if (isTop == 2) {
                    initDrop();
                    exchangeMarketAdapter.setDatas(dropDatas);
                }
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
                getDataBack(exchangeMarketAdapter.getDatas(), datas, exchangeMarketAdapter);
                defaultDatas.clear();
                defaultDatas.addAll(exchangeMarketAdapter.getDatas());
                if (defaultDatas.size() > 0) {
                    if (gainsState == 0) {
                        exchangeMarketAdapter.setDatas(defaultDatas);
                    } else if (gainsState == 1) {
                        initRise();
                        exchangeMarketAdapter.setDatas(riseDatas);
                    } else if (gainsState == 2) {
                        initDrop();
                        exchangeMarketAdapter.setDatas(dropDatas);
                    }
                }
                if (datas != null) {
                    //发送 更新请求
                    if (datas.size() > 0) {
                        sendWebSocket();
                    }
                }

                break;
        }
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
            HandlerHelper.getinstance().initHander(MarketValueFragment.this.getClass().getName(), exchangeDataMap, viewDelegate.getPullRecyclerView(), new HandlerHelper.OnUpdataLinsener() {
                @Override
                public void onUpdataLinsener(ExchangeData val) {
                    updataNew(val);
                }
            });
            WebSocketRequest.getInstance().addCallBack(MarketValueFragment.this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
                @Override
                public void onDataSuccess(String name, String data, String info, int status) {
                    if (MarketValueFragment.this.getClass().getName().equals(name)) {
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


    private void initRise() {
        if (riseDatas == null) {
            riseDatas = new ArrayList<>();
        } else {
            riseDatas.clear();
        }
        Rise24ChangeSort comparator = new Rise24ChangeSort();
        riseDatas.addAll(defaultDatas);
        Collections.sort(riseDatas, comparator);
    }

    private void initDrop() {
        if (dropDatas == null) {
            dropDatas = new ArrayList<>();
        } else {
            dropDatas.clear();
        }
        Drop24ChangeSort comparator = new Drop24ChangeSort();
        dropDatas.addAll(defaultDatas);
        Collections.sort(dropDatas, comparator);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
            //同步用户所选 单位
            if (exchangeMarketAdapter != null) {
                tv_unit.setText(UserSet.getinstance().getUnit());
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
        defaultDatas = new ArrayList<>();
        initList(new ArrayList<ExchangeData>());
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getAllMarketCaps(this));
    }

}
