package com.xiaomiquan.mvp.activity.chat;

import android.support.v7.widget.GridLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.view.GridSpacingItemDecoration;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ChatLiveListAdapter;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;

import java.util.ArrayList;
import java.util.List;

public class ChatLiveListActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    ChatLiveListAdapter chatLiveListAdapter;

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
                .setSubTitle(CommonUtils.getString(R.string.str_my_chat_live)));
        viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.mark_color));
        ArrayList<String> strings = new ArrayList<>();
        initList(strings);
    }

    private void initList(List<String> dats) {
        if (chatLiveListAdapter == null) {
            chatLiveListAdapter = new ChatLiveListAdapter(this, dats);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            viewDelegate.viewHolder.pull_recycleview.addItemDecoration(new GridSpacingItemDecoration(2, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_30px), true));
            initRecycleViewPull(chatLiveListAdapter, gridLayoutManager);
            onRefresh();
        } else {
            getDataBack(chatLiveListAdapter.getDatas(), dats, chatLiveListAdapter);
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<HotTeam> list = GsonUtil.getInstance().toList(data, HotTeam.class);

                break;
        }
    }

    @Override
    protected void refreshData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("");
        }
        initList(strings);
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
    }
}
