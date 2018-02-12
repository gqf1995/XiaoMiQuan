package com.xiaomiquan.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.ShareLabelHistoryTradingAdapter;
import com.xiaomiquan.entity.bean.group.HistoryTrading;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;

import java.util.ArrayList;
import java.util.List;

public class ShareHistoryTradingActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {
    public TextView tv_comment;
    ShareLabelHistoryTradingAdapter shareLabelHistoryTradingAdapter;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_share_history)));
        initTopAndBottom();
        initList(new ArrayList<HistoryTrading>());
        getIntentData();
    }

    private void initList(List<HistoryTrading> strDatas) {
        if (shareLabelHistoryTradingAdapter == null) {
            shareLabelHistoryTradingAdapter = new ShareLabelHistoryTradingAdapter(this, strDatas);
            initRecycleViewPull(shareLabelHistoryTradingAdapter, new LinearLayoutManager(this));
        } else {
            getDataBack(shareLabelHistoryTradingAdapter.getDatas(), strDatas, shareLabelHistoryTradingAdapter);
        }
    }

    private void initTopAndBottom() {
        View topView = getLayoutInflater().inflate(R.layout.layout_share_label_history_trading, null);
        viewDelegate.viewHolder.fl_pull.addView(topView, 0);
        View rootView = getLayoutInflater().inflate(R.layout.layout_bottm_commit, null);
        this.tv_comment = (TextView) rootView.findViewById(R.id.tv_comment);
        tv_comment.setText(CommonUtils.getString(R.string.str_select_complete));
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        viewDelegate.viewHolder.fl_pull.addView(rootView, 2);
    }

    private void commit() {
        if (shareLabelHistoryTradingAdapter.getSelectPositions().size() > 0) {
            //提交
            StringBuffer content = new StringBuffer();
            for (int i = 0; i < shareLabelHistoryTradingAdapter.getSelectPositions().size(); i++) {
                if (i != 0) {
                    content.append(",");
                }
                content.append(shareLabelHistoryTradingAdapter.getDatas().get(shareLabelHistoryTradingAdapter.getSelectPositions().get(i)));
            }
            addRequest(binder.articleSave(content.toString(), "3", "1", this));
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_select_history));
        }
    }


    public static void startAct(Activity activity,
                                String id) {
        Intent intent = new Intent(activity, ShareHistoryTradingActivity.class);
        intent.putExtra("id", "id");
        activity.startActivity(intent);
    }

    private String id;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data,info,status,requestCode);
        switch (requestCode) {
            case 0x123:
                List<HistoryTrading> datas = GsonUtil.getInstance().toList(data, HistoryTrading.class);
                initList(datas);
                break;
            case 0x124:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void refreshData() {
        viewDelegate.pagesize = 50;
        addRequest(binder.listDeal(id, "2", this));
    }

}
