package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.group.MyPropertyDetailAdapter;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.group.MyPropertyDetailBinder;
import com.xiaomiquan.mvp.delegate.group.MyPropertyDetailDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyPropertyDetailActivity extends BaseDataBindActivity<MyPropertyDetailDelegate, MyPropertyDetailBinder> {

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
        initToolbar(new ToolbarBuilder().setTitle(""));
        getIntentData();
        initList(new ArrayList<CoinDetail>());
    }

    private void initList(List<CoinDetail> coinDetails) {
        if (myPropertyDetailAdapter == null) {
            addRequest(binder.myCoin(groupItem.getId(), this));
            myPropertyDetailAdapter = new MyPropertyDetailAdapter(this, coinDetails);
            viewDelegate.viewHolder.pull_recycleview.setLayoutManager(new LinearLayoutManager(this));
            viewDelegate.viewHolder.pull_recycleview.setAdapter(myPropertyDetailAdapter);
        } else {
            myPropertyDetailAdapter.setDatas(coinDetails);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                groupItem = GsonUtil.getInstance().toList(data, GroupItem.class).get(0);
                break;
            case 0x124:
                List<CoinDetail> details = GsonUtil.getInstance().toList(data, CoinDetail.class);
                initList(details);
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


}
