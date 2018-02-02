package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.GetFriendsJoinBinder;
import com.xiaomiquan.mvp.databinder.circle.LiveBinder;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.List;

public class LiveActivity extends BaseDataBindActivity<LiveDelegate, LiveBinder> {

    SquareLiveAdapter squareLiveAdapter;

    @Override
    protected Class<LiveDelegate> getDelegateClass() {
        return LiveDelegate.class;
    }

    @Override
    public LiveBinder getDataBinder(LiveDelegate viewDelegate) {
        return new LiveBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("大V直播"));
        addRequest(binder.getLive(this));
    }

    public void initLive(final List<SquareLive> squareLives) {
        squareLiveAdapter = new SquareLiveAdapter(LiveActivity.this, squareLives);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                Intent intent = new Intent(LiveActivity.this, TopicDetailActivity.class);
                intent.putExtra("userTopic", (Serializable) squareLives.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.pull_recycleview.setAdapter(squareLiveAdapter);

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initLive(datas);
                break;
            case 0x124:

                break;
        }
    }

}
