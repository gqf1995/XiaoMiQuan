package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;

import java.util.ArrayList;

import skin.support.widget.SkinCompatLinearLayout;

public class AllGroupDelegate extends BaseFragentPullDelegate {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initRank();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_group;
    }

    private void initRank() {
        String[] string = CommonUtils.getStringArray(R.array.sa_select_group_rank);
        for (int i = 0; i < string.length; i++) {
            mTabEntities.add(new TabEntity(string[i], 0, 0));
        }
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_my_group_name1;
        public TextView tv_today_comein1;
        public TextView tv_all_comein1;
        public TextView tv_my_group_commit1;
        public LinearLayout lin_one_group;
        public TextView tv_my_group_name2;
        public TextView tv_today_comein2;
        public TextView tv_all_comein2;
        public TextView tv_my_group_commit2;
        public TextView tv_my_group_name3;
        public TextView tv_today_comein3;
        public TextView tv_all_comein3;
        public TextView tv_my_group_commit3;
        public LinearLayout lin_two_group;
        public CommonTabLayout tl_2;
        public RecyclerView rcv_hot_group;
        public RecyclerView pull_recycleview;
        public FrameLayout fl_rcv;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_my_group_name1 = (TextView) rootView.findViewById(R.id.tv_my_group_name1);
            this.tv_today_comein1 = (TextView) rootView.findViewById(R.id.tv_today_comein1);
            this.tv_all_comein1 = (TextView) rootView.findViewById(R.id.tv_all_comein1);
            this.tv_my_group_commit1 = (TextView) rootView.findViewById(R.id.tv_my_group_commit1);
            this.lin_one_group = (LinearLayout) rootView.findViewById(R.id.lin_one_group);
            this.tv_my_group_name2 = (TextView) rootView.findViewById(R.id.tv_my_group_name2);
            this.tv_today_comein2 = (TextView) rootView.findViewById(R.id.tv_today_comein2);
            this.tv_all_comein2 = (TextView) rootView.findViewById(R.id.tv_all_comein2);
            this.tv_my_group_commit2 = (TextView) rootView.findViewById(R.id.tv_my_group_commit2);
            this.tv_my_group_name3 = (TextView) rootView.findViewById(R.id.tv_my_group_name3);
            this.tv_today_comein3 = (TextView) rootView.findViewById(R.id.tv_today_comein3);
            this.tv_all_comein3 = (TextView) rootView.findViewById(R.id.tv_all_comein3);
            this.tv_my_group_commit3 = (TextView) rootView.findViewById(R.id.tv_my_group_commit3);
            this.lin_two_group = (LinearLayout) rootView.findViewById(R.id.lin_two_group);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.rcv_hot_group = (RecyclerView) rootView.findViewById(R.id.rcv_hot_group);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.fl_rcv = (FrameLayout) rootView.findViewById(R.id.fl_rcv);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}