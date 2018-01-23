package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.adapter.SearchAddCoinAdapter;
import com.xiaomiquan.mvp.databinder.SearchCoinMarketResultBinder;
import com.xiaomiquan.mvp.delegate.SearchCoinMarketResultDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchCoinMarketResultFragment extends BaseDataBindFragment<SearchCoinMarketResultDelegate, SearchCoinMarketResultBinder> {

    @Override
    protected Class<SearchCoinMarketResultDelegate> getDelegateClass() {
        return SearchCoinMarketResultDelegate.class;
    }

    @Override
    public SearchCoinMarketResultBinder getDataBinder(SearchCoinMarketResultDelegate viewDelegate) {
        return new SearchCoinMarketResultBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initList();
        search();
    }

    SearchAddCoinAdapter searchAddCoinAdapter;
    String strSearch;

    private void initList() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("");
        }
        searchAddCoinAdapter = new SearchAddCoinAdapter(getActivity(), data);
        searchAddCoinAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                searchAddCoinAdapter.select(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewDelegate.viewHolder.recycler_view.setAdapter(searchAddCoinAdapter);

    }

    public void searchInput(String txt) {
        strSearch = txt;
        if (searchAddCoinAdapter != null) {
            search();
        }
    }

    private void search() {
        //搜索
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

}
