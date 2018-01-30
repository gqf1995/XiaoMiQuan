package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.group.LabelHistoryEntrustAdapter;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史委托
 */
public class GroupHistoryEntrustFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    List<String> strDatas;
    LabelHistoryEntrustAdapter adapter;


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
    }


    private void initList(List<String> strDatas) {
        for (int i = 0; i < 20; i++) {
            strDatas.add("");
        }
        adapter = new LabelHistoryEntrustAdapter(getActivity(), strDatas);
        initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
        viewDelegate.setIsPullDown(false);
        onRefresh();
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
    protected void onFragmentFirstVisible() {
        strDatas = new ArrayList<>();
        initList(strDatas);
    }

    @Override
    protected void refreshData() {
        //addRequest(binder.listArticleByPage(this));
    }


}

