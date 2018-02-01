package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.LabelHistoryTradingAdapter;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建组合 成交历史
 */
public class HistoryTradingFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    List<String> strDatas;
    LabelHistoryTradingAdapter adapter;

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        strDatas = new ArrayList<>();
        initList(strDatas);
    }


    private void initList(List<String> strDatas) {
        for (int i = 0; i < 20; i++) {
            strDatas.add("");
        }
        adapter = new LabelHistoryTradingAdapter(getActivity(), strDatas);
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsPullDown(false);
        onRefresh();
        initTop();
    }

    private void initTop() {
        View rootView=getActivity().getLayoutInflater().inflate(R.layout.layout_label_history_trading,null);
        viewDelegate.viewHolder.fl_pull.addView(rootView,0);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<LiveData> data1 = GsonUtil.getInstance().toList(data, LiveData.class);
                getDataBack(strDatas, data1, adapter);
                break;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }



    @Override
    protected void refreshData() {
        //addRequest(binder.listArticleByPage(this));
    }
}
