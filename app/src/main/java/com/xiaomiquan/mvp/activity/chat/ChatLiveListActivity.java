package com.xiaomiquan.mvp.activity.chat;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.GridSpacingItemDecoration;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ChatLiveListAdapter;
import com.xiaomiquan.entity.bean.chat.ChatLiveItem;
import com.xiaomiquan.entity.bean.chat.CheckScore;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatLiveListActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    ChatLiveListAdapter chatLiveListAdapter;
    int selectPosition = 0;
    ChatLiveItem myGroup;

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
                    if (SingSettingDBUtil.isLogin(ChatLiveListActivity.this)) {
                        selectPosition = position;
                        //网络请求检测
                        addRequest(binder.checkScore(chatLiveListAdapter.getDatas().get(position).getGroupId(), ChatLiveListActivity.this));
                    }
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
            getDataBack(chatLiveListAdapter.getDatas(), dats, chatLiveListAdapter);
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //进入我的聊天室
        if (SingSettingDBUtil.isLogin(ChatLiveListActivity.this)) {
            if (myGroup != null) {
                GroupChatActivity.startAct(ChatLiveListActivity.this,
                        myGroup.getGroupId(),
                        myGroup.getGroupName(),
                        myGroup.getAvatar(),
                        myGroup.getTitle(),
                        myGroup.getOnlineTotal() + "",
                        true,
                        true,
                        0x123
                );
            } else {
                ToastUtil.show(CommonUtils.getString(R.string.str_warning_no_my_group));
            }
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<ChatLiveItem> list = GsonUtil.getInstance().toList(data, "list", ChatLiveItem.class);
                initList(list);
                myGroup = GsonUtil.getInstance().toObj(data, "myGroup", ChatLiveItem.class);
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
