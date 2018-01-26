package com.xiaomiquan.mvp.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.market.AddCoinActivity;
import com.xiaomiquan.mvp.activity.market.SortingUserCoinActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UserChooseFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    List<ExchangeData> strDatas;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    LinearLayoutManager linearLayoutManager;
    List<String> sendKeys;
    ArrayList<String> strings;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
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
                //                if (SingSettingDBUtil.isLogin(getActivity())) {
                //                    goChoose();
                //                }
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
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, datas, headerAndFooterWrapper);
                if (datas.size() > 0) {
                    //如果有数据 则底部显示添加自选按钮
                    lin_root.setVisibility(View.VISIBLE);
                } else {
                    lin_root.setVisibility(View.GONE);
                    viewDelegate.viewHolder.pull_recycleview.scrollToPosition(headerAndFooterWrapper.getItemCount() - 1);
                }
                //订阅推送
                sendWebSocket();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                onRefresh();
            }
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.marketdata(this));
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
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
            WebSocketRequest.getInstance().addCallBack(this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
                @Override
                public void onDataSuccess(String name, String data, String info, int status) {
                    if (this.getClass().getName().equals(name)) {
                        //推送数据

                    }
                }

                @Override
                public void onDataError(String name, String data, String info, int status) {

                }
            });
        } else {
            binder.cancelpost();
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
