package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.DropChangeSort;
import com.xiaomiquan.entity.RiseChangeSort;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.market.AddCoinActivity;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.activity.user.ChangeDefaultSetActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.utils.HandlerHelper;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.xiaomiquan.base.AppConst.CACHE_CHOOSE;

public class UserChooseFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    List<ExchangeData> defaultDatas;
    List<ExchangeData> riseDatas;
    List<ExchangeData> dropDatas;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    LinearLayoutManager linearLayoutManager;
    List<String> sendKeys;
    ArrayList<String> strings;
    UserLogin userLogin;
    boolean isOnRefush = false;//第一个页面 使用onFragmentVisibleChange有问题
    int sortingType = 0;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        initList(new ArrayList<ExchangeData>());
    }

    public ExchangeMarketAdapter getExchangeMarketAdapter() {
        return exchangeMarketAdapter;
    }

    public TextView tv_unit;
    public GainsTabView tv_rise;

    private void initTool() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_exchange_tool, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
        this.tv_rise = (GainsTabView) rootView.findViewById(R.id.tv_rise);
        tv_unit.setText(UserSet.getinstance().getUnit());
        tv_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_rise.onClick();
            }
        });
        tv_rise.setText(CommonUtils.getString(R.string.str_rise));
        tv_rise.setTextColor(CommonUtils.getColor(R.color.color_font2));
        tv_rise.setOnChange(new GainsTabView.OnChange() {
            @Override
            public void onChange(int isTop) {
                if (isTop == 0) {
                    exchangeMarketAdapter.setDatas(defaultDatas);
                } else if (isTop == 1) {
                    initRise();
                    exchangeMarketAdapter.setDatas(riseDatas);
                } else if (isTop == 2) {
                    initDrop();
                    exchangeMarketAdapter.setDatas(dropDatas);
                }
                headerAndFooterWrapper.notifyDataSetChanged();
            }
        });
        tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDefaultSetActivity.startAct(getActivity(), ChangeDefaultSetActivity.TYPE_UNIT);
            }
        });
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }

    private void initList(List<ExchangeData> coinData) {
        exchangeMarketAdapter = new ExchangeMarketAdapter(getActivity(), coinData);
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
        viewDelegate.setNoDataTxt(CommonUtils.getString(R.string.str_add_coin_market));
        viewDelegate.setNoDataClickListener(this);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(exchangeMarketAdapter);
        headerAndFooterWrapper.addFootView(initFootView());
        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return exchangeMarketAdapter.getDatas().size() > 0;
            }
        };
        initRecycleViewPull(headerAndFooterWrapper, headerAndFooterWrapper.getHeadersCount() + headerAndFooterWrapper.getFootersCount(), linearLayoutManager);
        viewDelegate.setIsLoadMore(false);
        defaultDatas = new ArrayList<>();
        onRefresh();
        initTool();
    }


    private void goChoose() {
        if (SingSettingDBUtil.isLogin(getActivity())) {
            if (strings == null) {
                strings = new ArrayList<>();
            } else {
                strings.clear();
            }
            for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
                strings.add(exchangeMarketAdapter.getDatas().get(i).getOnlyKey());
            }
            CacheUtils.getInstance().put(CACHE_CHOOSE, GsonUtil.getInstance().toJson(strings));
            AddCoinActivity.startAct(this, strings, 0x123);
        }
    }

    View rootView;
    private LinearLayout lin_add_coin_market;
    private LinearLayout lin_sorting;
    private LinearLayout lin_root;

    private View initFootView() {
        rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_bottom_add, null);
        this.lin_root = (LinearLayout) rootView.findViewById(R.id.lin_root);
        lin_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.lin_add_coin_market = (LinearLayout) rootView.findViewById(R.id.lin_add_coin_market);
        this.lin_sorting = (LinearLayout) rootView.findViewById(R.id.lin_sorting);
        lin_add_coin_market.setOnClickListener(this);
        lin_sorting.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_add_coin_market:
            case R.id.ic_nodata:
                //添加自选
                //检测登录
                goChoose();
                break;
            case R.id.lin_sorting:
                //列表排序
                gotoActivity(SortingUserCoinActivity.class).fragStartActResult(this, 0x123);
                break;
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                isOnRefush = false;
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(exchangeMarketAdapter.getDatas(), datas, headerAndFooterWrapper);
                defaultDatas.clear();
                defaultDatas.addAll(exchangeMarketAdapter.getDatas());
                if (defaultDatas.size() > 0) {
                    if (sortingType == 0) {
                        exchangeMarketAdapter.setDatas(defaultDatas);
                    } else if (sortingType == 1) {
                        initRise();
                        exchangeMarketAdapter.setDatas(riseDatas);
                    } else if (sortingType == 2) {
                        initDrop();
                        exchangeMarketAdapter.setDatas(dropDatas);
                    }
                    headerAndFooterWrapper.notifyDataSetChanged();
                }
                if (datas != null) {
                    if (datas.size() > 0) {
                        if (defaultDatas.size() > 0) {
                            //如果有数据 则底部显示添加自选按钮
                            lin_root.setVisibility(View.VISIBLE);
                        } else {
                            lin_root.setVisibility(View.GONE);
                            viewDelegate.viewHolder.pull_recycleview.scrollToPosition(headerAndFooterWrapper.getItemCount() - 1);
                        }
                        //订阅推送
                        sendWebSocket();
                    }
                }

                break;
        }
    }

    private void initRise() {
        if (riseDatas == null) {
            riseDatas = new ArrayList<>();
        } else {
            riseDatas.clear();
        }
        RiseChangeSort comparator = new RiseChangeSort();
        riseDatas.addAll(defaultDatas);
        Collections.sort(riseDatas, comparator);
    }

    private void initDrop() {
        if (dropDatas == null) {
            dropDatas = new ArrayList<>();
        } else {
            dropDatas.clear();
        }
        DropChangeSort comparator = new DropChangeSort();
        dropDatas.addAll(defaultDatas);
        Collections.sort(dropDatas, comparator);
    }


    @Override
    protected void refreshData() {
        if (userLogin != null) {
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            addRequest(binder.marketdata(this));
            isOnRefush = true;
        } else {
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
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

    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;
    int updataPosition = 0;

    //新数据推送 更新
    private void updataNew(ExchangeData data) {
        if (exchangeMarketAdapter == null) {
            return;
        }
        for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
            if (exchangeMarketAdapter.getDatas().get(i).getOnlyKey().equals(data.getOnlyKey())) {
                updataPosition = i;
                break;
            }
        }
        if (defaultDatas == null) {
            return;
        }
        for (int i = 0; i < defaultDatas.size(); i++) {
            if (defaultDatas.get(i).getOnlyKey().equals(data.getOnlyKey())) {
                defaultDatas.set(i, data);
                break;
            }
        }
        exchangeMarketAdapter.updataOne(updataPosition, data);
        headerAndFooterWrapper.notifyItemChanged(updataPosition + headerAndFooterWrapper.getHeadersCount());
    }

    private void initWebSocketRequest() {
        if (exchangeDataMap == null) {
            exchangeDataMap = new ConcurrentHashMap<>();
            //handler.sendEmptyMessageDelayed(whatIndex, 1000);
        }
        if (viewDelegate != null) {
            HandlerHelper.getinstance().initHander(UserChooseFragment.this.getClass().getName(), exchangeDataMap, viewDelegate.getPullRecyclerView(), new HandlerHelper.OnUpdataLinsener() {
                @Override
                public void onUpdataLinsener(ExchangeData val) {
                    updataNew(val);
                }
            });
            WebSocketRequest.getInstance().addCallBack(UserChooseFragment.this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
                @Override
                public void onDataSuccess(String name, String data, String info, int status) {
                    if (UserChooseFragment.this.getClass().getName().equals(name)) {
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
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible && !isOnRefush) {
            binder.cancelpost();
            onRefresh();
            if (exchangeMarketAdapter != null) {
                tv_unit.setText(UserSet.getinstance().getShowUnit());
                if (exchangeMarketAdapter.getDatas().size() > 0) {
                    exchangeMarketAdapter.notifyDataSetChanged();
                }
            }
        } else if(!isVisible){
            binder.cancelpost();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
            isOnRefush = false;
        }
    }


    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }


}
