package com.xiaomiquan.mvp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.SearchAddCoinAdapter;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchCoinMarketDefaultFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    List<String> strDatas;
    SearchAddCoinAdapter searchAddCoinAdapter;
    HeaderAndFooterWrapper adapter;
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        strDatas = new ArrayList<>();
        initList(strDatas);
    }

    public TextView tv_clean_history;
    public TagFlowLayout id_flowlayout;
    public LinearLayout lin_history;
    public LinearLayout lin_hot;

    private View initFlowlayout() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_default_search_top, null);
        this.tv_clean_history = (TextView) rootView.findViewById(R.id.tv_clean_history);
        this.id_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_flowlayout);
        this.lin_history = (LinearLayout) rootView.findViewById(R.id.lin_history);
        this.lin_hot = (LinearLayout) rootView.findViewById(R.id.lin_hot);
        lin_hot.setVisibility(View.GONE);
        //搜索 缓存查找历史记录

        id_flowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.layout_flowtext,
                        parent, false);
                tv.setText(mVals[position]);
                return tv;
            }
        });
        tv_clean_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lin_history.setVisibility(View.GONE);
            }
        });
        return rootView;
    }

    private void initList(List<String> strDatas) {
        searchAddCoinAdapter = new SearchAddCoinAdapter(getActivity(), strDatas);
        adapter = new HeaderAndFooterWrapper(searchAddCoinAdapter);
        adapter.addHeaderView(initFlowlayout());
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsLoadMore(false);
        viewDelegate.setIsPullDown(false);
        onRefresh();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:

                break;
        }
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
    protected void refreshData() {

    }

}
