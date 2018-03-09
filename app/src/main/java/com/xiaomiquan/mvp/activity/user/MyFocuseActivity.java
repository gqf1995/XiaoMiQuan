package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.FocuseAdapter;
import com.xiaomiquan.entity.bean.Participant;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFocuseActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    FocuseAdapter focuseAdapter;

    @Override
    protected Class<BaseActivityPullDelegate> getDelegateClass() {
        return BaseActivityPullDelegate.class;
    }

    @Override
    public BaseActivityPullBinder getDataBinder(BaseActivityPullDelegate viewDelegate) {
        return new BaseActivityPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_focuse_people)));
        initList(new ArrayList<Participant>());
    }

    private void initList(List<Participant> dats) {
        if (focuseAdapter == null) {
            focuseAdapter = new FocuseAdapter(this, dats);
            focuseAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            initRecycleViewPull(focuseAdapter, new LinearLayoutManager(this));
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        } else {
            getDataBack(focuseAdapter.getDatas(), dats, focuseAdapter);
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<Participant> list = GsonUtil.getInstance().toList(data, Participant.class);
                initList(list);
                break;
        }
    }

    public static void startAct(Activity activity,
                                String id) {
        Intent intent = new Intent(activity, MyFocuseActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private String id;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void refreshData() {
        addRequest(binder.attentionUserList(id, this));
    }
}
