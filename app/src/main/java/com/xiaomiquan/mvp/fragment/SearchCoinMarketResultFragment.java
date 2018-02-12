package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.adapter.SearchAddCoinAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_SEARCH_HISTORY;

public class SearchCoinMarketResultFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    List<String> searchHistory;

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
        String string = CacheUtils.getInstance().getString(CACHE_SEARCH_HISTORY);
        searchHistory = GsonUtil.getInstance().toList(string, String.class);
        if (searchHistory == null) {
            searchHistory = new ArrayList<>();
        }
        initList();
        onRefresh();
    }


    List<String> userChooseCoin;
    SearchAddCoinAdapter searchAddCoinAdapter;
    String strSearch;
    List<ExchangeData> strDatas;

    public void setUserChooseCoin(List<String> userChooseCoin) {
        this.userChooseCoin = userChooseCoin;
    }

    private void initList() {
        strDatas = new ArrayList<>();
        searchAddCoinAdapter = new SearchAddCoinAdapter(getActivity(), strDatas);
        searchAddCoinAdapter.setUserSelectKeys(userChooseCoin);
        searchAddCoinAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                if (searchAddCoinAdapter.getUserSelectKeys().contains(searchAddCoinAdapter.getDatas().get(position).getOnlyKey())) {
                    searchAddCoinAdapter.getUserSelectKeys().remove(searchAddCoinAdapter.getUserSelectKeys().indexOf(searchAddCoinAdapter.getDatas().get(position).getOnlyKey()));
                    //取消 订阅
                    binder.singlesubs(searchAddCoinAdapter.getDatas().get(position).getOnlyKey(), "0", null);
                } else {
                    searchAddCoinAdapter.getUserSelectKeys().add(searchAddCoinAdapter.getDatas().get(position).getOnlyKey());
                    //订阅
                    binder.singlesubs(searchAddCoinAdapter.getDatas().get(position).getOnlyKey(), "1", null);
                    if (!searchHistory.contains(strSearch)) {
                        //添加到搜索历史
                        searchHistory.add(strSearch);
                        CacheUtils.getInstance().put(CACHE_SEARCH_HISTORY, GsonUtil.getInstance().toJson(searchHistory));
                    }
                }
                searchAddCoinAdapter.notifyItemChanged(position);
            }
        });
        initRecycleViewPull(searchAddCoinAdapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsPullDown(false);
        viewDelegate.setDefaultPage(0);
    }

    public void searchInput(String txt) {
        strSearch = txt;
        if (searchAddCoinAdapter != null) {
            onRefresh();
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data,info,status,requestCode);
        switch (requestCode) {
            case 0x123:
                List<ExchangeData> exchangeData = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, exchangeData, searchAddCoinAdapter);
                break;
        }
    }

    @Override
    protected void refreshData() {
        binder.cancelpost();
        addRequest(binder.getAllMarketByExchangeOrSymbol(strSearch, this));
    }
}
