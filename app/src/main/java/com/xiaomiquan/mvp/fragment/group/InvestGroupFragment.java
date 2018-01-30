package com.xiaomiquan.mvp.fragment.group;

import android.support.v4.app.Fragment;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.group.CreatGroupActivity;
import com.xiaomiquan.mvp.databinder.ComTabViewpageBinder;
import com.xiaomiquan.mvp.delegate.ComTabViewpageDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvestGroupFragment extends BaseDataBindFragment<ComTabViewpageDelegate, ComTabViewpageBinder> {

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected Class<ComTabViewpageDelegate> getDelegateClass() {
        return ComTabViewpageDelegate.class;
    }

    @Override
    public ComTabViewpageBinder getDataBinder(ComTabViewpageDelegate viewDelegate) {
        return new ComTabViewpageBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_investment_combination)).setSubTitle(CommonUtils.getString(R.string.str_creat_combination)).setShowBack(false));
        initTablelayout();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        gotoActivity(CreatGroupActivity.class).startAct();
    }

    @Override

    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    private void initTablelayout() {
        mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_group));
        fragments = new ArrayList<>();
        fragments.add(new AllGroupFragment());
        fragments.add(new CompetitionGroupFragment());
        fragments.add(new MyGocuseGroupFragment());
        fragments.add(new MyGroupFragment());
        for (int i = 0; i < mTitles.size(); i++) {

            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.initViewpager(fragments, mTabEntities, getChildFragmentManager(), mTitles);
    }

}
