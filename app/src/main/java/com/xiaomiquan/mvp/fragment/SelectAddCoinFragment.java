package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.AddCoinAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SelectAddCoinFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    String type;
    AddCoinAdapter addCoinAdapter;
    List<ExchangeData> strDatas;
    boolean isCoin = false;
    List<String> userSelectKeys;

    public void setUserSelectKeys(List<String> userSelectKeys) {
        this.userSelectKeys = userSelectKeys;
        if (addCoinAdapter != null) {
            //重置已选中自选
            addCoinAdapter.setUserSelectKeys(userSelectKeys);
        }
    }

    public void setCoin(boolean coin) {
        //设置是否是 根据币自选
        isCoin = coin;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        type = getArguments().getString("type");
        initList();
    }

    private void initList() {
        strDatas = new ArrayList<>();
        addCoinAdapter = new AddCoinAdapter(getActivity(), strDatas);
        addCoinAdapter.setCoin(isCoin);
        addCoinAdapter.setUserSelectKeys(userSelectKeys);
        addCoinAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                EventBus.getDefault().post(addCoinAdapter.getDatas().get(position));
                addCoinAdapter.select(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        initRecycleViewPull(addCoinAdapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsLoadMore(false);
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                getDataBack(strDatas, datas, addCoinAdapter);
                break;
        }
    }


    @Override
    protected void refreshData() {
        addRequest(binder.show(type, this));
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            //每次进入后重新刷新,防止重复
            if (addCoinAdapter != null) {
                addCoinAdapter.notifyDataSetChanged();
            }
        }
    }

    public static SelectAddCoinFragment newInstance(
            String type) {
        SelectAddCoinFragment newFragment = new SelectAddCoinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type")) {
            type = savedInstanceState.getString("type");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", type);
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
