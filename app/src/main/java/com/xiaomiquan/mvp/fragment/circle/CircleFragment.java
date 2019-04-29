package com.xiaomiquan.mvp.fragment.circle;

import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.mvp.databinder.circle.CircleBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleDelegate;
import com.xiaomiquan.widget.circle.SquarePopupWindow;

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
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.str_release)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        viewDelegate.getmToolbarBack().setVisibility(View.GONE);
        mTitles = CommonUtils.getStringArray(R.array.sa_select_circle);
        fragments = new ArrayList<>();
        fragments.add(new SquareFragment());
        fragments.add(new CircleShowFragment());
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.viewpager,
                mTitles, getActivity(), fragments);

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        final SquarePopupWindow squarePopupWindow = new SquarePopupWindow(getActivity());
        squarePopupWindow.setOnItemClickListener(new SquarePopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.lin_dynamic:
                        ReleaseDynamicActivity.startAct(getActivity(), "2", "1");
                        squarePopupWindow.dismiss();
                        break;
                    case R.id.lin_article:
                        ReleaseArticleActivity.startAct(getActivity(), "1", "1","0");
                        squarePopupWindow.dismiss();
                        break;
                    case R.id.lin_wechat:
                        ReleaseArticleActivity.startAct(getActivity(), "1", "1","1");
                        squarePopupWindow.dismiss();
                        break;
                    case R.id.btn_cancel:
                        squarePopupWindow.dismiss();
                        break;

                }
            }
        });
        squarePopupWindow.showAtLocation(viewDelegate.viewHolder.tl_2, Gravity.BOTTOM, 0, 0);

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
