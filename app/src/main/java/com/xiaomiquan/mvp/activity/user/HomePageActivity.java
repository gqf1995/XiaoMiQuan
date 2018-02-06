package com.xiaomiquan.mvp.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.DynamicAdapter;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.HomePageDelegate;
import com.xiaomiquan.widget.DropDownView;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BasePullActivity<HomePageDelegate, BaseActivityPullBinder> {
    DynamicAdapter dynamicAdapter;

    HeaderAndFooterWrapper adapter;
    MyGroupAdapter myGroupAdapter;

    @Override
    protected Class<HomePageDelegate> getDelegateClass() {
        return HomePageDelegate.class;
    }

    @Override
    public BaseActivityPullBinder getDataBinder(HomePageDelegate viewDelegate) {
        return new BaseActivityPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
        viewDelegate.initToplinsener((int) CommonUtils.getDimensionPixelSize(R.dimen.trans_150px));
        initList(new ArrayList<String>());
    }

    private void initList(List<String> datas) {
        for (int i = 0; i < 20; i++) {
            datas.add("");
        }
        if (dynamicAdapter == null) {
            dynamicAdapter = new DynamicAdapter(this, datas);
            adapter = new HeaderAndFooterWrapper(dynamicAdapter);
            adapter.addHeaderView(initTop());
            initRecycleViewPull(adapter, new LinearLayoutManager(this));
            viewDelegate.setIsLoadMore(false);
            onRefresh();
            addRequest(binder.listDemo(this));
        } else {
            getDataBack(dynamicAdapter.getDatas(), datas, adapter);
        }
    }

    public TextView tv_name;
    public TextView tv_user_name;
    public TextView tv_fucose_people_num;
    public DropDownView dp_theme;
    public DropDownView dp_sorting;

    private View initTop() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_home_page, null);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_user_name = (TextView) rootView.findViewById(R.id.tv_user_name);
        this.tv_fucose_people_num = (TextView) rootView.findViewById(R.id.tv_fucose_people_num);
        this.dp_theme = (DropDownView) rootView.findViewById(R.id.dp_theme);
        this.dp_sorting = (DropDownView) rootView.findViewById(R.id.dp_sorting);
        return rootView;
    }



    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {

        }
    }

    @Override
    protected void refreshData() {

    }

}
