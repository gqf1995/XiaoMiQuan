package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.circle.BigVListAdapter;
import com.xiaomiquan.entity.bean.circle.User;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.xiaomiquan.mvp.databinder.circle.LiveBinder;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class FansActivity extends BaseDataBindActivity<LiveDelegate, LiveBinder> {

    BigVListAdapter bigVListAdapter;

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
        initToolbar(new ToolbarBuilder().setTitle("粉丝列表"));
        getIntentData();
        addRequest(binder.getFans(user.getId(),this));
    }

    public void initLive(final List<UserFriende> squareLives) {
        bigVListAdapter = new BigVListAdapter(FansActivity.this, squareLives);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FansActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        bigVListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
//                //加入圈子
//                CircleDialogHelper.initDefaultDialog(FansActivity.this, "确定关注吗？", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        addRequest(binder.attention(squareLives.get(position).getId(), "1", FansActivity.this));
//                    }
//                }).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.pull_recycleview.setAdapter(bigVListAdapter);

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x127:
                List<UserFriende> datas = GsonUtil.getInstance().toList(data, UserFriende.class);
                initLive(datas);
                break;
        }
    }

    User user;

    private void getIntentData() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

}