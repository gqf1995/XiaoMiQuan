package com.xiaomiquan.mvp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.mvp.databinder.CircleBinder;
import com.xiaomiquan.mvp.delegate.CircleDelegate;

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
        initToolbar(new ToolbarBuilder().setTitle("").setmRightImg1(CommonUtils.getString(R.string.ic_Chart)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        viewDelegate.setBackIconFontText(CommonUtils.getString(R.string.ic_Search1));
        viewDelegate.getViewImgPoint().setVisibility(View.VISIBLE);
        initBarClick();
        mTitles = CommonUtils.getStringArray(R.array.sa_select_circle);
        fragments = new ArrayList<>();
        fragments.add(new SquareFragment());
        fragments.add(new CircleDvpFragment());
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.viewpager,
                mTitles, getActivity(), fragments);

    }

    private void initBarClick() {
        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索
                WebActivityActivity.startAct(getActivity(), "https://www.baidu.com/?tn=47018152_dg");
            }
        });
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
