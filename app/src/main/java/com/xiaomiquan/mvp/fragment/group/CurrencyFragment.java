package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.GroupDealCurrencyAdapter;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.TradingResult;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 币种列表
 */
public class CurrencyFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    GroupDealCurrencyAdapter adapter;
    public static final String TYPE_CURRENCY_BUY = "type_currency_buy";
    public static final String TYPE_CURRENCY_SELL = "type_currency_sell";
    public String demoId;
    public String type;
    public String searchOrId = "";

    public void setDemoId(String demoId) {
        this.demoId = demoId;
        onRefresh();
    }

    public String getDemoId() {
        return demoId;
    }

    public void setSearchOrId(String searchOrId) {
        this.searchOrId = searchOrId;
        if (adapter != null) {
            binder.cancelpost();
            onRefresh();
        }
    }

    public interface OnSelectLinsener {
        void onSelectLinsener(CoinDetail coinDetail);

        void upData(boolean isUpdataNow);

        void onUpdata(String data);
    }

    OnSelectLinsener onSelectLinsener;


    public void setOnSelectLinsener(OnSelectLinsener onSelectLinsener) {
        this.onSelectLinsener = onSelectLinsener;
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
        type = getArguments().getString("type");
        searchOrId = getArguments().getString("searchOrId");
        initList(new ArrayList<CoinDetail>());
    }

    public void setNewDatas(String type, String searchOrId) {
        adapter = null;
        this.type = type;
        this.searchOrId = searchOrId;
        initList(new ArrayList<CoinDetail>());
    }

    private void initList(List<CoinDetail> strDatas) {
        if (adapter == null) {
            adapter = new GroupDealCurrencyAdapter(getActivity(), strDatas);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    adapter.setSelectPosition(position);
                    onSelectLinsener.onSelectLinsener(adapter.getDatas().get(position));
                    //单个刷新
                    onSelectLinsener.upData(true);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            layoutManager.setSmoothScrollbarEnabled(true);
            layoutManager.setAutoMeasureEnabled(true);
            initRecycleViewPull(adapter, layoutManager);
            viewDelegate.viewHolder.pull_recycleview.setHasFixedSize(true);
            viewDelegate.viewHolder.pull_recycleview.setNestedScrollingEnabled(false);
            viewDelegate.viewHolder.pull_recycleview.setVerticalScrollBarEnabled(true);
            viewDelegate.viewHolder.pull_recycleview.setScrollBarSize((int) CommonUtils.getDimensionPixelSize(R.dimen.trans_5px));
            viewDelegate.setIsPullDown(false);
            viewDelegate.setNoDataImgId(0);
            viewDelegate.setNoDataTxt(CommonUtils.getString(R.string.str_now_no_data));
            if (TYPE_CURRENCY_SELL.equals(type)) {
                viewDelegate.setIsLoadMore(false);
            }
            onRefresh();
        } else {
            getDataBack(adapter.getDatas(), strDatas, adapter);
            if (strDatas.size() > 0) {
                adapter.setSelectPosition(0);
                if (onSelectLinsener != null) {
                    onSelectLinsener.onSelectLinsener(adapter.getDatas().get(0));
                }
            } else {
                if (onSelectLinsener != null) {
                    onSelectLinsener.onSelectLinsener(null);
                }
            }
        }
    }

    public void getSelectPositionData(String unit) {
        if (onSelectLinsener != null && adapter != null) {
            if (adapter.getDatas().size() > 0) {
                if (TYPE_CURRENCY_SELL.equals(type)) {
                    if (!TextUtils.isEmpty(unit)) {
                        adapter.setSelectDefault(unit);
                    }
                }
                onSelectLinsener.onSelectLinsener(adapter.getDatas().get(adapter.getSelectPosition()));
            } else {
                onSelectLinsener.onSelectLinsener(null);
            }
        }
    }

    public void setSellResult(TradingResult tradingResult) {
        if (adapter != null) {
            adapter.getDatas().get(adapter.getSelectPosition()).setCount(tradingResult.getCount());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<CoinDetail> datas = GsonUtil.getInstance().toList(data, CoinDetail.class);
                initList(datas);
                if (datas != null) {
                    if (datas.size() > 0) {
                        if (TYPE_CURRENCY_BUY.equals(type)) {
                            if (onSelectLinsener != null) {
                                onSelectLinsener.onSelectLinsener(adapter.getDatas().get(adapter.getSelectPosition()));
                            }
                        }
                    }
                }
                break;
            case 0x124:
                if (onSelectLinsener != null) {
                    onSelectLinsener.onUpdata(data);
                }
                break;
        }
    }


    @Override
    protected void refreshData() {
        if (adapter != null) {
            if (TYPE_CURRENCY_BUY.equals(type)) {
                addRequest(binder.searchCoin(searchOrId, demoId, this));
            } else if (TYPE_CURRENCY_SELL.equals(type)) {
                addRequest(binder.myCoin(searchOrId, this));
            }
        }
    }

    //刷新币种价格
    public void onUpdata() {
        if (adapter != null) {
            if (ListUtils.isEmpty(adapter.getDatas())) {
                addRequest(binder.getCurrPrice(adapter.getDatas().get(adapter.getSelectPosition()).getCoinId(), this));
            }
        }
    }

    public void canalUpdata() {
        if (binder != null) {
            binder.cancelpost();
        }
    }

    public static CurrencyFragment newInstance(
            String demoId,
            String type,
            String searchOrId
    ) {
        CurrencyFragment newFragment = new CurrencyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("demoId", demoId);
        bundle.putString("type", type);
        bundle.putString("searchOrId", searchOrId);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type")
                && savedInstanceState.containsKey("demoId")
                && savedInstanceState.containsKey("searchOrId")) {
            type = savedInstanceState.getString("type");
            demoId = savedInstanceState.getString("demoId");
            searchOrId = savedInstanceState.getString("searchOrId");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchOrId", searchOrId);
        outState.putString("type", type);
        outState.putString("demoId", demoId);
    }


}
