package com.xiaomiquan.mvp.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ExchangeMarketAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.market.AddCoinActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import skin.support.widget.SkinCompatCardView;

import static android.app.Activity.RESULT_OK;

public class UserChooseFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    ExchangeMarketAdapter exchangeMarketAdapter;
    List<ExchangeData> strDatas;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    LinearLayoutManager linearLayoutManager;

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
        viewDelegate.setNoDataClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检测登录
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    goChoose();
                }
            }
        });
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

    public SkinCompatCardView card_root;

    private void goChoose() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < exchangeMarketAdapter.getDatas().size(); i++) {
            strings.add(exchangeMarketAdapter.getDatas().get(i).getOnlyKey());
        }
        AddCoinActivity.startAct(this, strings, 0x123);
    }

    View rootView;

    private View initFootView() {
        rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_bottom_add, null);
        this.card_root = (SkinCompatCardView) rootView.findViewById(R.id.card_root);
        card_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        card_root.setVisibility(View.GONE);
        card_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChoose();
            }
        });
        return rootView;
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, datas, headerAndFooterWrapper);
                if (datas.size() > 0) {
                    //如果有数据 则底部显示添加自选按钮
                    card_root.setVisibility(View.VISIBLE);
                } else {
                    card_root.setVisibility(View.GONE);
                }
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
