package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.SearchAddCoinAdapter;
import com.xiaomiquan.mvp.databinder.SearchCoinMarketDefaultBinder;
import com.xiaomiquan.mvp.delegate.SearchCoinMarketDefaultDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchCoinMarketDefaultFragment extends BaseDataBindFragment<SearchCoinMarketDefaultDelegate, SearchCoinMarketDefaultBinder> {

    String[] data = {
            "btc", "btc21321", "btc123", "btc432",
            "btc21321", "btc123", "btc432",
    };

    SearchAddCoinAdapter searchAddCoinAdapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initFlowlayout();
        initList();
    }

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
        viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.recycler_view.setAdapter(searchAddCoinAdapter);

    }

    private void initFlowlayout() {
        viewDelegate.viewHolder.id_flowlayout.setAdapter(new TagAdapter<String>(Arrays.asList(data)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.layout_flowtext,
                        viewDelegate.viewHolder.id_flowlayout, false);
                ((TextView) view.findViewById(R.id.tv_title)).setText(s);
                return view;
            }
        });
        viewDelegate.viewHolder.tv_clean_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDelegate.viewHolder.lin_history.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

    @Override
    protected Class<SearchCoinMarketDefaultDelegate> getDelegateClass() {
        return SearchCoinMarketDefaultDelegate.class;
    }

    @Override
    public SearchCoinMarketDefaultBinder getDataBinder(SearchCoinMarketDefaultDelegate viewDelegate) {
        return new SearchCoinMarketDefaultBinder(viewDelegate);
    }

}
