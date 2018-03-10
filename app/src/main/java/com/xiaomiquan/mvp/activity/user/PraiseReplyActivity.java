package com.xiaomiquan.mvp.activity.user;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.PraiseReplyAdapter;
import com.xiaomiquan.entity.bean.MessageInfo;
import com.xiaomiquan.entity.bean.PraiseReplyItem;
import com.xiaomiquan.greenDB.MessageInfoDao;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;

import java.util.ArrayList;
import java.util.List;

public class PraiseReplyActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    PraiseReplyAdapter adapter;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_praise_reply)));
        initList(new ArrayList<PraiseReplyItem>());

        List<MessageInfo> list = DaoManager.getInstance().getDaoSession().getMessageInfoDao()
                .queryBuilder()
                .where(MessageInfoDao.Properties.IsLook.eq(false))
                .list();
        for (int i = 0; i < list.size(); i++) {
            MessageInfo messageInfo = list.get(i);
            messageInfo.setLook(true);
            DaoManager.getInstance().getDaoSession().getMessageInfoDao().update(messageInfo);
        }

    }

    private void initList(List<PraiseReplyItem> dats) {
        if (adapter == null) {
            adapter = new PraiseReplyAdapter(this, dats);
            initRecycleViewPull(adapter, new LinearLayoutManager(this));
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        } else {
            getDataBack(adapter.getDatas(), dats, adapter);
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<PraiseReplyItem> list = GsonUtil.getInstance().toList(data, PraiseReplyItem.class);
                initList(list);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.listPraiseOrReply(this));
    }
}
