package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.circledialog.res.drawable.RadiuBg;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.SearchAddCoinAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_SEARCH_HISTORY;

public class SearchCoinMarketDefaultFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    List<ExchangeData> strDatas;
    SearchAddCoinAdapter searchAddCoinAdapter;
    HeaderAndFooterWrapper adapter;
    List<String> history;

    public interface OnClickHistory {
        void onClickHistory(String history);
    }

    OnClickHistory onClickHistory;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        onClickHistory = (OnClickHistory) context;
    }

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
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.tv_clean_history = (TextView) rootView.findViewById(R.id.tv_clean_history);
        this.id_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_flowlayout);
        this.lin_history = (LinearLayout) rootView.findViewById(R.id.lin_history);
        this.lin_hot = (LinearLayout) rootView.findViewById(R.id.lin_hot);
        lin_hot.setVisibility(View.GONE);
        //搜索 缓存查找历史记录
        String string = CacheUtils.getInstance().getString(CACHE_SEARCH_HISTORY);
        if (TextUtils.isEmpty(string)) {
            lin_history.setVisibility(View.GONE);
        } else {
            history = GsonUtil.getInstance().toList(string, String.class);
            id_flowlayout.setAdapter(new TagAdapter<String>(history) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.layout_flowtext,
                            parent, false);
                    tv.setBackground(new RadiuBg(CommonUtils.getColor(R.color.base_mask), 1000, 1000, 1000, 1000));
                    tv.setText(s);
                    return tv;
                }
            });
            id_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    onClickHistory.onClickHistory(history.get(position));
                    return false;
                }
            });
            tv_clean_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lin_history.setVisibility(View.GONE);
                    CacheUtils.getInstance().put(CACHE_SEARCH_HISTORY, "");
                }
            });
        }
        return rootView;
    }

    private void initList(List<ExchangeData> strDatas) {
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
