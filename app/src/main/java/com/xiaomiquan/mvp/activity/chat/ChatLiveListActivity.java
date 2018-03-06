package com.xiaomiquan.mvp.activity.chat;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.view.GridSpacingItemDecoration;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ChatLiveListAdapter;
import com.xiaomiquan.entity.bean.chat.ChatLiveItem;
import com.xiaomiquan.entity.bean.chat.CheckScore;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatLiveListActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    ChatLiveListAdapter chatLiveListAdapter;
    int selectPosition = 0;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_chat_live_list))
                .setSubTitle(" "));
        initList(new ArrayList<ChatLiveItem>());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            //修改群资料后刷新
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    private void initList(List<ChatLiveItem> dats) {
        if (chatLiveListAdapter == null) {
            chatLiveListAdapter = new ChatLiveListAdapter(this, dats);
            chatLiveListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    selectPosition = position;
                    //网络请求检测
                    addRequest(binder.checkScore(chatLiveListAdapter.getDatas().get(position).getGroupId(), ChatLiveListActivity.this));
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            viewDelegate.viewHolder.pull_recycleview.addItemDecoration(new GridSpacingItemDecoration(2, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_30px), true));
            initRecycleViewPull(chatLiveListAdapter, gridLayoutManager);
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        } else {
            viewDelegate.getmToolbarSubTitle().setText(CommonUtils.getString(R.string.str_my_chat_live));
            viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.mark_color));
            getDataBack(chatLiveListAdapter.getDatas(), dats, chatLiveListAdapter);
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<ChatLiveItem> list = GsonUtil.getInstance().toList(data, ChatLiveItem.class);
                initList(list);
                break;
            case 0x124:
                //检测进群条件结果
                CheckScore checkScore = GsonUtil.getInstance().toObj(data, CheckScore.class);
                if (!checkScore.isJoinGroup()) {
                    CircleDialogHelper.initDefaultToastDialog(this, CommonUtils.getString(R.string.str_toast_cannot_join_group), null)
                            .show();
                    return;
                }
                GroupChatActivity.startAct(ChatLiveListActivity.this,
                        chatLiveListAdapter.getDatas().get(selectPosition).getGroupId(),
                        chatLiveListAdapter.getDatas().get(selectPosition).getGroupName(),
                        chatLiveListAdapter.getDatas().get(selectPosition).getAvatar(),
                        chatLiveListAdapter.getDatas().get(selectPosition).getTitle(),
                        chatLiveListAdapter.getDatas().get(selectPosition).getOnlineTotal() + "",
                        checkScore.isLeader(),
                        checkScore.isCanSpeak(),
                        0x123
                );
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.chatRoomList(this));
    }
}
