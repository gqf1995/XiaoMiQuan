package com.xiaomiquan.mvp.fragment.group;

import android.support.v4.app.Fragment;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.CreatGroupActivity;
import com.xiaomiquan.mvp.databinder.ComTabViewpageBinder;
import com.xiaomiquan.mvp.delegate.ComTabViewpageDelegate;
import com.xiaomiquan.utils.UserSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 跟投
 */
public class InvestGroupFragment extends BaseDataBindFragment<ComTabViewpageDelegate, ComTabViewpageBinder> {

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    boolean isRedRise = false;
    int pagePosition=0;

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
        isRedRise = UserSet.getinstance().isRedRise();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_investment_combination)).setSubTitle(CommonUtils.getString(R.string.str_creat_combination)));
        viewDelegate.getmToolbarBack().setText(CommonUtils.getString(R.string.ic_Message));
        viewDelegate.getmToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initTablelayout();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if(SingSettingDBUtil.isLogin(getActivity())){
            gotoActivity(CreatGroupActivity.class).startAct();
        }
    }

    @Override

    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    public void checkRedRise() {
        //红涨绿跌变化检测
        if (isRedRise != UserSet.getinstance().isRedRise()) {
            if (fragments != null) {
                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i) instanceof AllGroupFragment) {
                        ((AllGroupFragment) fragments.get(i)).notifyDataSetChanged();
                    } else if (fragments.get(i) instanceof CompetitionGroupFragment) {
                        ((CompetitionGroupFragment) fragments.get(i)).notifyDataSetChanged();
                    } else if (fragments.get(i) instanceof MyFocuseGroupFragment) {
                        ((MyFocuseGroupFragment) fragments.get(i)).notifyDataSetChanged();
                    } else if (fragments.get(i) instanceof MyGroupFragment) {
                        ((MyGroupFragment) fragments.get(i)).notifyDataSetChanged();
                    }
                }
            }
        }
        isRedRise = UserSet.getinstance().isRedRise();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            checkRedRise();
        }
    }

    public void toPage(int pagePosition) {
        this.pagePosition=pagePosition;
        if (fragments != null) {
            viewDelegate.viewHolder.vp_sliding.setCurrentItem(pagePosition);
        }
    }

    private void initTablelayout() {
        mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_group));
        fragments = new ArrayList<>();
        fragments.add(new AllGroupFragment());
        fragments.add(new CompetitionGroupFragment());
        fragments.add(new MyFocuseGroupFragment());
        fragments.add(new MyGroupFragment());
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.initViewpager(fragments, mTabEntities, getChildFragmentManager(), mTitles);
        viewDelegate.viewHolder.vp_sliding.setCurrentItem(pagePosition);
    }

}
