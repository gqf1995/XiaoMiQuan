package com.xiaomiquan.mvp.fragment.circle;

import android.support.v4.app.Fragment;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.mvp.databinder.circle.CircleBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleDelegate;

import java.util.ArrayList;

public class CircleFragment extends BaseDataBindFragment<CircleDelegate, CircleBinder> {

    String[] mTitles;
    ArrayList<Fragment> fragments;

    @Override
    protected Class<CircleDelegate> getDelegateClass() {
        return CircleDelegate.class;
    }

    @Override
    public CircleBinder getDataBinder(CircleDelegate viewDelegate) {
        return new CircleBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        mTitles = CommonUtils.getStringArray(R.array.sa_select_circle);
        fragments = new ArrayList<>();
        fragments.add(new SquareFragment());
        fragments.add(new CircleShowFragment());
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.viewpager,
                mTitles, getActivity(), fragments);

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
