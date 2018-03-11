package com.xiaomiquan.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.UserCenterListAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建组合 成交历史
 */
public class UserCenterListFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    UserCenterListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("id")) {
            id = savedInstanceState.getString("id");
            type = savedInstanceState.getString("type");
        }
    }

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
        id = getArguments().getString("id");
        type = getArguments().getString("type");
        initList(new ArrayList<SquareLive>());
    }


    private void initList(List<SquareLive> strDatas) {
        if (adapter == null) {
            adapter = new UserCenterListAdapter(getActivity(), strDatas);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if ("1".equals(adapter.getDatas().get(position).getType())) {
                        ArticleDetailsActivity.startAct(getActivity(), adapter.getDatas().get(position),"");
                    } else if ("2".equals(adapter.getDatas().get(position).getType())) {
                        TopicDetailActivity.startAct(getActivity(), adapter.getDatas().get(position),"");
                    } else if ("3".equals(adapter.getDatas().get(position).getType())) {
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            initRecycleViewPull(adapter, new LinearLayoutManager(getActivity()));
            viewDelegate.setIsPullDown(false);
        } else {
            getDataBack(adapter.getDatas(), strDatas, adapter);
        }
        //onRefresh();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<SquareLive> data1 = GsonUtil.getInstance().toList(data, SquareLive.class);
                initList(data1);
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
        addRequest(binder.listByUserAndType(id, "0".equals(type) ? null : type, this));
    }

    public static UserCenterListFragment newInstance(
            String id,
            String type
    ) {
        UserCenterListFragment newFragment = new UserCenterListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    String id;
    String type;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", id);
        outState.putString("type", type);
    }

}
