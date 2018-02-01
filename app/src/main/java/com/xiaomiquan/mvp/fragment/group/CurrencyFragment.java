package com.xiaomiquan.mvp.fragment.group;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.group.GroupDealCurrencyAdapter;
import com.xiaomiquan.entity.bean.group.CoinDetail;
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
    public String type;
    public String searchOrId = "";

    public void setSearchOrId(String searchOrId) {
        this.searchOrId = searchOrId;
        if (adapter != null) {
            onRefresh();
        }
    }

    public interface OnSelectLinsener {
        void onSelectLinsener(CoinDetail coinDetail);
    }

    OnSelectLinsener onSelectLinsener;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        onSelectLinsener = (OnSelectLinsener) context;
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


    private void initList(List<CoinDetail> strDatas) {
        if (adapter == null) {
            adapter = new GroupDealCurrencyAdapter(getActivity(), strDatas);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    adapter.setSelectPosition(position);
                    onSelectLinsener.onSelectLinsener(adapter.getDatas().get(position));
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
            //设置分页长度
            if (TYPE_CURRENCY_BUY.equals(type)) {
                viewDelegate.pagesize = 50;
            } else if (TYPE_CURRENCY_BUY.equals(type)) {
                viewDelegate.pagesize = 999;
            }
            initRecycleViewPull(adapter, layoutManager);
            viewDelegate.viewHolder.pull_recycleview.setHasFixedSize(true);
            viewDelegate.viewHolder.pull_recycleview.setNestedScrollingEnabled(false);
            viewDelegate.setIsPullDown(false);
            onRefresh();
        } else {
            getDataBack(adapter.getDatas(), strDatas, adapter);
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
                        onSelectLinsener.onSelectLinsener(adapter.getDatas().get(adapter.getSelectPosition()));
                    }
                }
                break;
        }
    }


    @Override
    protected void refreshData() {
        if (TYPE_CURRENCY_BUY.equals(type)) {
            addRequest(binder.searchCoin(searchOrId, this));
        } else if (TYPE_CURRENCY_BUY.equals(type)) {
            addRequest(binder.myCoin(searchOrId, this));
        }
        //addRequest(binder.listArticleByPage(this));
    }


    public static CurrencyFragment newInstance(
            String type,
            String searchOrId
    ) {
        CurrencyFragment newFragment = new CurrencyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("searchOrId", searchOrId);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type") && savedInstanceState.containsKey("searchOrId")) {
            type = savedInstanceState.getString("type");
            searchOrId = savedInstanceState.getString("searchOrId");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchOrId", searchOrId);
        outState.putString("type", type);
    }


}
