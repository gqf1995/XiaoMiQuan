package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.adapter.group.RevenueRankingAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 收益排行
 */
public class RevenueRankingFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    RevenueRankingAdapter revenueRankingAdapter;
    UserLogin userLogin;

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
        userLogin = SingSettingDBUtil.getUserLogin();
        initList(new ArrayList<GroupItem>());
    }

    @Override
    protected void onFragmentFirstVisible() {
        type = getArguments().getString("type");
        addRequest(binder.top(type, this));
    }

    private void initList(List<GroupItem> rankList) {
        if (revenueRankingAdapter == null) {
            revenueRankingAdapter = new RevenueRankingAdapter(getActivity(), rankList, type);
            revenueRankingAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    //跳转他人详情
                    if (userLogin != null) {
                        if ((userLogin.getId() + "").equals(revenueRankingAdapter.getDatas().get(position).getUserId())) {
                            CombinationActivity.startAct(getActivity(), revenueRankingAdapter.getDatas().get(position), true);
                        } else {
                            CombinationActivity.startAct(getActivity(), revenueRankingAdapter.getDatas().get(position), false);
                        }
                    } else {
                        CombinationActivity.startAct(getActivity(), revenueRankingAdapter.getDatas().get(position), false);
                    }
                    //HisAccountActivity.startAct(getActivity(), revenueRankingAdapter.getDatas().get(position).getId());
                }
            });
            initRecycleViewPull(revenueRankingAdapter, new LinearLayoutManager(getActivity()));
        } else {
            getDataBack(revenueRankingAdapter.getDatas(), rankList, revenueRankingAdapter);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x124:
                List<GroupItem> data1 = GsonUtil.getInstance().toList(GsonUtil.getInstance().getValue(data, "tops"), GroupItem.class);
                initList(data1);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.top(type, this));
    }

    public static RevenueRankingFragment newInstance(
            String type
    ) {
        RevenueRankingFragment newFragment = new RevenueRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type")) {
            type = savedInstanceState.getString("type");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", type);
    }

}

