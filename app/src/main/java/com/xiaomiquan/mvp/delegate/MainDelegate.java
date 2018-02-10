package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UserSet;

import java.util.ArrayList;

public class MainDelegate extends IMDelegate {
    public ViewHolder viewHolder;
    private String[] mTitles = {"", "", "", ""};
    private int[] mIconSelectIds = {
            R.string.ic_Chart, R.string.ic_Layers,
            R.string.ic_Inbox, R.string.ic_User};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        //初始化皮肤
        UserSet.getinstance().setNight(UserSet.getinstance().isNight());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initBottom(OnTabSelectListener onTabSelectListener) {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], 0));
        }
        viewHolder.tl_2.setIconVisible(false);
        viewHolder.tl_2.setmIndicatorId(R.drawable.shape_blue_maxradiu);
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.tl_2.setOnTabSelectListener(onTabSelectListener);
    }

    @Override
    public void ImError() {
        if (imLinsener != null) {
            imLinsener.ImError();
        }
    }

    @Override
    public void ImSuccess() {
        if (imLinsener != null) {
            imLinsener.ImSuccess();
        }
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