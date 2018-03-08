package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UserSet;

import java.util.ArrayList;

public class MainDelegate extends IMDelegate {
    public ViewHolder viewHolder;
    private String[] mTitles = {CommonUtils.getString(R.string.str_home),
            CommonUtils.getString(R.string.str_market),
            CommonUtils.getString(R.string.str_user),
            CommonUtils.getString(R.string.str_user)
    };
    private int[] mIconSelectIds = {
            R.drawable.ic_hebingxingzhuang,
            R.drawable.ic_hebingxingzhuang,
            R.drawable.ic_hebingxingzhuang,
            R.drawable.ic_hebingxingzhuang
    };
    private int[] mIconUnSelectIds = {
            R.drawable.ic_combined_shape,
            R.drawable.ic_combined_shape,
            R.drawable.ic_combined_shape,
            R.drawable.ic_combined_shape
    };
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
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        viewHolder.tl_2.setIconVisible(true);
        //viewHolder.tl_2.setmIndicatorId(R.drawable.shape_blue_maxradiu);
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
        public FrameLayout fl_left;
        public DrawerLayout main_drawer_layout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.fl_root = (FrameLayout) rootView.findViewById(R.id.fl_root);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.fl_left = (FrameLayout) rootView.findViewById(R.id.fl_left);
            this.main_drawer_layout = (DrawerLayout) rootView.findViewById(R.id.main_drawer_layout);
        }

    }
}