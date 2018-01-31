package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.adapter.group.GroupDealCurrencyAdapter;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 币种列表
 */
public class CurrencyFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    List<String> strDatas;
    GroupDealCurrencyAdapter adapter;


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
        initList(new ArrayList<String>());
    }


    private void initList(List<String> strDatas) {
        if (adapter == null) {
            for (int i = 0; i < 20; i++) {
                strDatas.add("BTC");
            }
            adapter = new GroupDealCurrencyAdapter(getActivity(), strDatas);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    adapter.setSelectPosition(position);
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
            viewDelegate.pagesize = 50;
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
                break;
        }
    }


    @Override
    protected void refreshData() {
        //addRequest(binder.listArticleByPage(this));
    }
}
