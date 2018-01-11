package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;

import java.util.ArrayList;

public class MainDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    private String[] mTitles = {"", "", "", ""};
    private int[] mIconSelectIds = {
            R.string.ic_zhankai, R.string.ic_zhankai,
            R.string.ic_zhankai, R.string.ic_zhankai};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initBottom();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initBottom() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], 0));
        }
        viewHolder.tl_2.setIconVisible(false);
        viewHolder.tl_2.setmIndicatorId(R.drawable.shape_blue_maxradiu);
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public static class ViewHolder {
        public View rootView;
        public FrameLayout fl_root;
        public CommonTabLayout tl_2;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.fl_root = (FrameLayout) rootView.findViewById(R.id.fl_root);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
        }

    }
}