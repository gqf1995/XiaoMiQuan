package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyPropertyDetailAdapter;
import com.xiaomiquan.entity.bean.MyAsset;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.group.MyPropertyDetailBinder;
import com.xiaomiquan.mvp.delegate.group.MyPropertyDetailDelegate;

import java.util.ArrayList;
import java.util.List;

public class MyPropertyDetailActivity extends BasePullActivity<MyPropertyDetailDelegate, MyPropertyDetailBinder> {

    MyPropertyDetailAdapter myPropertyDetailAdapter;

    @Override
    protected Class<MyPropertyDetailDelegate> getDelegateClass() {
        return MyPropertyDetailDelegate.class;
    }

    @Override
    public MyPropertyDetailBinder getDataBinder(MyPropertyDetailDelegate viewDelegate) {
        return new MyPropertyDetailBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_tv_assets_report)));
        getIntentData();
        initList(new ArrayList<MyAsset.CoinsBean>());
    }

    private void initList(List<MyAsset.CoinsBean> datas) {
        if (myPropertyDetailAdapter == null) {
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            myPropertyDetailAdapter = new MyPropertyDetailAdapter(this, datas);
            initRecycleViewPull(myPropertyDetailAdapter, new LinearLayoutManager(this));
            viewDelegate.setIsLoadMore(false);
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    MyPropertyDetailActivity.this.refreshData();
                }
            });
        } else {
            getDataBack(myPropertyDetailAdapter.getDatas(), datas, myPropertyDetailAdapter);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x126:
                MyAsset myAsset = GsonUtil.getInstance().toObj(data, MyAsset.class);
                initList(myAsset.getCoins());
                viewDelegate.initMyAsset(myAsset);
                break;

        }
    }

    public static void startAct(Activity activity,
                                GroupItem groupItem
    ) {
        Intent intent = new Intent(activity, MyPropertyDetailActivity.class);
        intent.putExtra("groupItem", groupItem);
        activity.startActivity(intent);
    }

    private GroupItem groupItem;

    private void getIntentData() {
        Intent intent = getIntent();
        groupItem = intent.getParcelableExtra("groupItem");
        viewDelegate.viewHolder.tv_usable_usd.setText(groupItem.getBalance());
    }

    @Override
    protected void refreshData() {
        addRequest(binder.myAsset(groupItem.getId(), MyPropertyDetailActivity.this));
    }
}
