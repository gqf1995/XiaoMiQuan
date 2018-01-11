package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.tablayout.SlidingTabLayout;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.fragment.UserFragment;
import com.xiaomiquan.widget.GainsTabView;

import java.util.ArrayList;

public class MarketDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    ArrayList<Fragment> fragments;
    String[] mTitles;
    GainsTabView gainsTabView;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initTablelayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market;
    }

    private void initTablelayout() {
        if (mTitles == null && fragments == null) {
            fragments = new ArrayList<>();
            mTitles = CommonUtils.getStringArray(R.array.sa_select_market);
        }
        for (int i = 0; i < mTitles.length; i++) {
            fragments.add(new UserFragment());
        }
        viewHolder.tl_2.setViewPager(viewHolder.vp_sliding,
                mTitles, (FragmentActivity) viewHolder.rootView.getContext(), fragments);

        LinearLayout linearLayout = viewHolder.tl_2.getmTabsContainer();
        gainsTabView = new GainsTabView(viewHolder.rootView.getContext());
        gainsTabView.setDefault(viewHolder.tl_2.getmTabsContainer().getChildAt(2));
        linearLayout.removeViewAt(2);
        linearLayout.addView(gainsTabView, 2);
        gainsTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.tl_2.setCurrentTab(2);
                gainsTabView.onClick();
            }
        });
        gainsTabView.setOnChange(new GainsTabView.OnChange() {
            @Override
            public void onChange(boolean isTop) {
                // 涨幅 选择 上涨 还是下跌
            }
        });
    }

    public static class ViewHolder {
        public View rootView;
        public SlidingTabLayout tl_2;
        public ViewPager vp_sliding;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_2 = (SlidingTabLayout) rootView.findViewById(R.id.tl_2);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
        }

    }
}