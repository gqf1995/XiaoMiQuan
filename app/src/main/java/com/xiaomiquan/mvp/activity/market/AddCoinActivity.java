package com.xiaomiquan.mvp.activity.market;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.mvp.fragment.SelectAddCoinFragment;

import java.util.ArrayList;

public class AddCoinActivity extends BaseDataBindActivity<TabViewpageDelegate, TabViewpageBinder> {

    ArrayList<Fragment> fragments;
    String[] mTitles;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_add_coin_market)).setSubTitle(CommonUtils.getString(R.string.str_complete)));
        initTablelayout();
    }

    private void initTablelayout() {
        if (mTitles == null && fragments == null) {
            fragments = new ArrayList<>();
            mTitles = CommonUtils.getStringArray(R.array.sa_select_market);
        }

        for (int i = 0; i < mTitles.length; i++) {
            fragments.add(SelectAddCoinFragment.newInstance(mTitles[i]));
        }
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles, (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected Class<TabViewpageDelegate> getDelegateClass() {
        return TabViewpageDelegate.class;
    }

    @Override
    public TabViewpageBinder getDataBinder(TabViewpageDelegate viewDelegate) {
        return new TabViewpageBinder(viewDelegate);
    }

}
