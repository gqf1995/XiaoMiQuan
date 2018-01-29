package com.xiaomiquan.mvp.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
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
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xiaomiquan.base.AppConst.CACHE_CHOOSE;

public class UserChooseFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    List<ExchangeData> strDatas;
    List<ExchangeData> defaultDatas;
    List<ExchangeData> riseDatas;
    List<ExchangeData> dropDatas;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    LinearLayoutManager linearLayoutManager;
    List<String> sendKeys;
    ArrayList<String> strings;
    UserLogin userLogin;
    boolean isOnRefush = false;//第一个页面 使用onFragmentVisibleChange有问题

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
    }

    public ExchangeMarketAdapter getExchangeMarketAdapter() {
        return exchangeMarketAdapter;
    }

    public TextView tv_unit;
    public GainsTabView tv_rise;
    int gainsState = 0;

    private void initTool() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_exchange_tool, null);
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
                gainsState = isTop;
                if (isTop == 0) {
                    exchangeMarketAdapter.setDatas(defaultDatas);
                } else if (isTop == 1) {
                    exchangeMarketAdapter.setDatas(riseDatas);
                } else if (isTop == 2) {
                    exchangeMarketAdapter.setDatas(dropDatas);
                }
            }
        });
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }

    private void initList(List<ExchangeData> coinData) {
        exchangeMarketAdapter = new ExchangeMarketAdapter(getActivity(), coinData);
        exchangeMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

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
        initTool();
    }


    private void goChoose() {
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
                defaultDatas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, defaultDatas, headerAndFooterWrapper);
                if (defaultDatas.size() > 0) {
                    //如果有数据 则底部显示添加自选按钮
                    lin_root.setVisibility(View.VISIBLE);
                } else {
                    lin_root.setVisibility(View.GONE);
                    viewDelegate.viewHolder.pull_recycleview.scrollToPosition(headerAndFooterWrapper.getItemCount() - 1);
                }
                initRise();
                initDrop();
                //订阅推送
                sendWebSocket();
                break;
        }
    }

    private void initRise() {
        if (riseDatas == null) {
            riseDatas = Collections.synchronizedList(new ArrayList());
        } else {
            riseDatas.clear();
        }
        RiseChangeSort comparator = new RiseChangeSort();
        riseDatas.addAll(defaultDatas);
        Collections.sort(riseDatas, comparator);
    }

    private void initDrop() {
        if (dropDatas == null) {
            dropDatas = Collections.synchronizedList(new ArrayList());
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
            addRequest(binder.marketdata(this));
            isOnRefush = true;
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
            WebSocketRequest.getInstance().sendData(sendKeys);
        }
    }

    private ConcurrentHashMap<String, ExchangeData> exchangeDataMap;
    final int whatIndex = 1023;
    @SuppressLint("HandlerLeak")
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

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible && !isOnRefush) {
            onRefresh();
            WebSocketRequest.getInstance().addCallBack(this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
                @Override
                public void onDataSuccess(String name, String data, String info, int status) {
                    if (this.getClass().getName().equals(name)) {
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
        } else if (isVisible) {
            //同步用户所选 单位
            if (exchangeMarketAdapter != null) {
                tv_unit.setText(UserSet.getinstance().getShowUnit());
                if (exchangeMarketAdapter.getDatas().size() > 0) {
                    exchangeMarketAdapter.notifyDataSetChanged();
                }
            }
        } else {
            binder.cancelpost();
            isOnRefush = false;
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        strDatas = new ArrayList<>();
        initList(strDatas);
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
