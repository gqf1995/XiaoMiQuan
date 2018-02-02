package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.group.GroupChangeBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注组合
 */
public class MyGocuseGroupFragment extends BasePullFragment<BaseFragentPullDelegate, GroupChangeBinder> {

    MyGroupAdapter myGroupAdapter;

    @Override
    public GroupChangeBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new GroupChangeBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initList();
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    private void initList() {
        List<GroupItem> datas = new ArrayList<>();
        myGroupAdapter = new MyGroupAdapter(getActivity(), datas);
        myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, final int position, Object item) {
                if (view.getId() == R.id.tv_deal) {
                    gotoActivity(GroupDealActivity.class).startAct();
                }
                if (view.getId() == R.id.tv_look) {
                    gotoActivity(CombinationActivity.class).startAct();
                }
            }
        });
        initRecycleViewPull(myGroupAdapter, new LinearLayoutManager(getActivity()));
        onRefresh();
    }

    @Override
    protected void refreshData() {

    }
}
