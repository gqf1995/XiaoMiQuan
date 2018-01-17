package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.github.mikephil.charting.charts.LineChart;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.mpchart.ConstantTest;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.widget.chart.CoinLineDraw;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExchangeFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    HeaderAndFooterWrapper adapter;
    ExchangeName exchangeName;
    List<ExchangeData> strDatas;

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


    private void initList() {
        strDatas = new ArrayList<>();
        exchangeMarketAdapter = new ExchangeMarketAdapter(getActivity(), strDatas);
        exchangeMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                MarketDetailsActivity.startAct(getActivity(), exchangeMarketAdapter.getDatas().get(position - adapter.getHeadersCount()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        adapter = new HeaderAndFooterWrapper(exchangeMarketAdapter);
        adapter.addHeaderView(initTopView());
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(getActivity()));
        viewDelegate.setIsLoadMore(false);
    }

    public FontTextview tv_coin_type;
    public FontTextview tv_coin_name;
    public FontTextview tv_coin_market_value;
    public FontTextview tv_coin_price;
    public FontTextview tv_coin_probably;
    public FontTextview tv_gains;
    public LinearLayout lin_root;
    public LineChart linechart;

    private View initTopView() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_coin_market_top, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.tv_coin_type = (FontTextview) rootView.findViewById(R.id.tv_coin_type);
        this.tv_coin_name = (FontTextview) rootView.findViewById(R.id.tv_coin_name);
        this.tv_coin_market_value = (FontTextview) rootView.findViewById(R.id.tv_coin_market_value);
        this.tv_coin_price = (FontTextview) rootView.findViewById(R.id.tv_coin_price);
        this.tv_coin_probably = (FontTextview) rootView.findViewById(R.id.tv_coin_probably);
        this.tv_gains = (FontTextview) rootView.findViewById(R.id.tv_gains);
        this.lin_root = (LinearLayout) rootView.findViewById(R.id.lin_root);
        this.linechart = (LineChart) rootView.findViewById(R.id.linechart);
        tv_coin_type.setTextColor(CommonUtils.getColor(R.color.white));
        tv_coin_name.setTextColor(CommonUtils.getColor(R.color.white));
        tv_coin_market_value.setTextColor(CommonUtils.getColor(R.color.white));
        tv_coin_price.setTextColor(CommonUtils.getColor(R.color.white));
        tv_coin_probably.setTextColor(CommonUtils.getColor(R.color.white));
        tv_gains.setTextColor(CommonUtils.getColor(R.color.white));
        lin_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(MarketDetailsActivity.class).startAct();
            }
        });
        getOffLineData();
        return rootView;
    }

    private void getOffLineData() {
           /*方便测试，加入假数据*/
        DataParse mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);
        CoinLineDraw coinLineDraw = new CoinLineDraw();
        coinLineDraw.setData(mData, linechart);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, datas, adapter);
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
        initList();
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
}
