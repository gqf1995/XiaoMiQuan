package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class CompetitionGroupDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competition;
    }


    public static class ViewHolder {
        public View rootView;
        public LinearLayout lin_add_team;
        public LinearLayout lin_create_team;
        public LinearLayout lin_my_reward;
        public LinearLayout lin_activity_rules;
        public TextView tv_end_time;
        public TextView tv_more;
        public LinearLayout lin_invite_friends;
        public LinearLayout lin_my_create;
        public RecyclerView rv_my_create_team;
        public LinearLayout lin_my_join;
        public RecyclerView rv_my_join_team;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lin_add_team = (LinearLayout) rootView.findViewById(R.id.lin_add_team);
            this.lin_create_team = (LinearLayout) rootView.findViewById(R.id.lin_create_team);
            this.lin_my_reward = (LinearLayout) rootView.findViewById(R.id.lin_my_reward);
            this.lin_activity_rules = (LinearLayout) rootView.findViewById(R.id.lin_activity_rules);
            this.tv_end_time = (TextView) rootView.findViewById(R.id.tv_end_time);
            this.tv_more = (TextView) rootView.findViewById(R.id.tv_more);
            this.lin_invite_friends = (LinearLayout) rootView.findViewById(R.id.lin_invite_friends);
            this.lin_my_create = (LinearLayout) rootView.findViewById(R.id.lin_my_create);
            this.rv_my_create_team = (RecyclerView) rootView.findViewById(R.id.rv_my_create_team);
            this.lin_my_join = (LinearLayout) rootView.findViewById(R.id.lin_my_join);
            this.rv_my_join_team = (RecyclerView) rootView.findViewById(R.id.rv_my_join_team);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}