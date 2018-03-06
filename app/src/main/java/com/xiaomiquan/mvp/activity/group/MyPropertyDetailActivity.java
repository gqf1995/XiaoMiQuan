package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.adapter.group.MyPropertyDetailAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.databinder.group.MyPropertyDetailBinder;
import com.xiaomiquan.mvp.delegate.group.MyPropertyDetailDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyPropertyDetailActivity extends BaseDataBindActivity<MyPropertyDetailDelegate, MyPropertyDetailBinder> {

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
        initList();
    }

    private void initList() {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            str.add("" + i);
        }
        MyPropertyDetailAdapter myPropertyDetailAdapter = new MyPropertyDetailAdapter(this, str);
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(new LinearLayoutManager(this));
        viewDelegate.viewHolder.pull_recycleview.setAdapter(myPropertyDetailAdapter);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
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

    }


}
