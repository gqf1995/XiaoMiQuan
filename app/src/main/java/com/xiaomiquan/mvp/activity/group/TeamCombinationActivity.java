package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.TeamInfo;
import com.xiaomiquan.mvp.databinder.CombinationBinder;
import com.xiaomiquan.mvp.delegate.CombinationDelegate;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.GroupNotDealFragment;

import java.util.ArrayList;

/**
 * 大赛组合详情
 */
public class TeamCombinationActivity extends BaseDataBindActivity<CombinationDelegate, CombinationBinder> {
    ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected Class<CombinationDelegate> getDelegateClass() {
        return CombinationDelegate.class;
    }

    @Override
    public CombinationBinder getDataBinder(CombinationDelegate viewDelegate) {
        return new CombinationBinder(viewDelegate);
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //        if (isMy) {
        //            //修改简介
        //            EditIntroductionActivity.startAct(this, groupItem.getId(), groupItem.getBrief(), groupItem.getSync(), 0x123);
        //        } else {
        //            //取消关注 或 关注
        //            if (groupItem.getIsAttention() == 0) {
        //                binder.cancelAttention(groupItem.getId(), null);
        //                viewDelegate.getmToolbarSubTitle().setText(CommonUtils.getString(R.string.str_focuse));
        //            } else if (groupItem.getIsAttention() == 1) {
        //                binder.demoattention(groupItem.getUserId(), null);
        //                viewDelegate.getmToolbarSubTitle().setText(CommonUtils.getString(R.string.str_cancel_fucose));
        //            } else if (groupItem.getIsAttention() == 2) {
        //
        //            }
        //        }
    }

    public static void startAct(Activity activity,
                                String id
    ) {
        Intent intent = new Intent(activity, TeamCombinationActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    String id;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        addRequest(binder.demoInfo(id, this));
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.demoInfo(id, TeamCombinationActivity.this));
            }
        });
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(""));
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                String brief = data.getStringExtra("brief");
                String sync = data.getStringExtra("sync");

            }
        }
    }

    @Override
    protected void onServiceError(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                TeamInfo teamInfo = GsonUtil.getInstance().toObj(data, TeamInfo.class);
                viewDelegate.initTeaminfo(teamInfo);
                initToolbar(new ToolbarBuilder().setTitle(teamInfo.getName()));
                break;
        }
    }

    private void initViews() {
        if (!ListUtils.isEmpty(getSupportFragmentManager().getFragments())) {
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_combination);
            fragments = new ArrayList<>();
            fragments.add(GroupDetailListFragment.newInstance(id));
            fragments.add(GroupNotDealFragment.newInstance(id));
            fragments.add(GroupHistoryTradingFragment.newInstance(id));
            fragments.add(GroupHistoryEntrustFragment.newInstance(id));
            for (int i = 0; i < stringArray.length; i++) {
                mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            }
            viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
            InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, stringArray);
            viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.viewpager);
        }
    }

}
