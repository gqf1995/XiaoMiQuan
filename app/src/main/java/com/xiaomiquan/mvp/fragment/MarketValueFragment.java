package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.CoinMarketAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.activity.market.MarketDetailsActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.GainsTabView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import skin.support.widget.SkinCompatLinearLayout;

public class MarketValueFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    CoinMarketAdapter exchangeMarketAdapter;
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
    }


    private void initList(List<ExchangeData> strDatas) {
        if (exchangeMarketAdapter == null) {
            exchangeMarketAdapter = new CoinMarketAdapter(getActivity(), strDatas);
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
            initRecycleViewPull(exchangeMarketAdapter, new LinearLayoutManager(getActivity()));
            initTool();
        }
    }

    public TextView tv_unit;
    public GainsTabView tv_rise;
    public SkinCompatLinearLayout lin_root;

    private void initTool() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_exchange_tool, null);
        this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
        this.tv_rise = (GainsTabView) rootView.findViewById(R.id.tv_rise);
        this.lin_root = (SkinCompatLinearLayout) rootView.findViewById(R.id.lin_root);
        tv_unit.setText(UserSet.getinstance().getUnit());

        tv_rise.setText(CommonUtils.getString(R.string.str_rise));
        tv_rise.setTextColor(CommonUtils.getColor(R.color.color_font2));
        tv_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_rise.onClick();
            }
        });
        viewDelegate.viewHolder.fl_pull.addView(rootView, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        //同步用户所选 单位
        tv_unit.setText(UserSet.getinstance().getShowUnit());
        if (exchangeMarketAdapter != null) {
            if (exchangeMarketAdapter.getDatas().size() > 0) {
                exchangeMarketAdapter.setDefaultUnit(UserSet.getinstance().getUnit().replaceAll("-", "\n"));
            }
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, datas, exchangeMarketAdapter);
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
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getAllMarketCaps(this));
    }

}
