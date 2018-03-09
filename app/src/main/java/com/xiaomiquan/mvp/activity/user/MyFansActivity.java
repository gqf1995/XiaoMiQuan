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
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.FansAdapter;
import com.xiaomiquan.entity.bean.Participant;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFansActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    FansAdapter fansAdapter;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_fans)));
        initList(new ArrayList<Participant>());
    }

    private void initList(List<Participant> dats) {
        if (fansAdapter == null) {
            fansAdapter = new FansAdapter(this, dats);
            fansAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            fansAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (SingSettingDBUtil.getUserLogin() != null) {
                        if (id.equals(SingSettingDBUtil.getUserLogin().getId() + "")) {
                            //关注
                            if (!fansAdapter.getDatas().get(position).isAttentionMyfans()) {
                                addRequest(binder.attention(fansAdapter.getDatas().get(position).getId() + "", MyFansActivity.this));
                                fansAdapter.getDatas().get(position).setAttentionMyfans(true);
                                fansAdapter.notifyItemChanged(position);
                            }
                        }
                    }
                }
            });
            initRecycleViewPull(fansAdapter, new LinearLayoutManager(this));
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        } else {
            getDataBack(fansAdapter.getDatas(), dats, fansAdapter);
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
        Intent intent = new Intent(activity, MyFansActivity.class);
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
        addRequest(binder.attentionMyList(id, this));
    }
}
