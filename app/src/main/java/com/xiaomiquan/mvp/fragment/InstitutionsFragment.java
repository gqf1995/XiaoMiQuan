package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.github.mikephil.charting.charts.LineChart;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.CoinMarketAdapter;
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

public class InstitutionsFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    CoinMarketAdapter coinMarketAdapter;
    HeaderAndFooterWrapper adapter;

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
        initList();
    }

    private void initList() {
        List<String> coinData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            coinData.add("");
        }
        coinMarketAdapter = new CoinMarketAdapter(getActivity(), coinData);
        coinMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        adapter = new HeaderAndFooterWrapper(coinMarketAdapter);
        adapter.addHeaderView(initTopView());
        viewDelegate.setIsLoadMore(false);
        initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(getActivity()));
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
        }
    }

    @Override
    protected void refreshData() {

    }
}
