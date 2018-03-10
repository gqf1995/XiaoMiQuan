package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的组合
 */
public class MyGroupFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    MyGroupAdapter myGroupAdapter;
    UserLogin userLogin;

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<GroupItem> datas = GsonUtil.getInstance().toList(data, GroupItem.class);
                initList(datas);
                break;
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        if (userLogin != null) {
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        }
    }

    public void notifyDataSetChanged() {
        if (myGroupAdapter != null) {
            myGroupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    private void initList(List<GroupItem> datas) {
        if (myGroupAdapter == null) {
            myGroupAdapter = new MyGroupAdapter(getActivity(), datas);
            myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, final int position, Object item) {
                    if (view.getId() == R.id.tv_deal) {
                        //SimulatedTradingFragment.startAct(getActivity(), (ArrayList)myGroupAdapter.getDatas(),position, true);
                    }
                    if (view.getId() == R.id.tv_look) {
                        //MyAccountActivity.startAct(getActivity(), myGroupAdapter.getDatas().get(position));
                    }
                }
            });
            initRecycleViewPull(myGroupAdapter, new LinearLayoutManager(getActivity()));
        } else {
            getDataBack(myGroupAdapter.getDatas(), datas, myGroupAdapter);
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
        initList(new ArrayList<GroupItem>());
    }

    @Override
    protected void refreshData() {
        if (userLogin != null) {
            addRequest(binder.listDemo(this));
        } else {
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
