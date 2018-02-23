package com.xiaomiquan.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatToolbar;

public class TeamDetailDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_detail;
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public TextView tv_name;
        public TextView tv_nick_name;
        public TextView tv_rate;
        public TextView tv_content;
        public TextView tv_team_people_num;
        public TextView tv_team_earnings;
        public TextView tv_apply_to_join;
        public RecyclerView rv_my_group;
        public RecyclerView rv_team_rank;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_nick_name = (TextView) rootView.findViewById(R.id.tv_nick_name);
            this.tv_rate = (TextView) rootView.findViewById(R.id.tv_rate);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.tv_team_people_num = (TextView) rootView.findViewById(R.id.tv_team_people_num);
            this.tv_team_earnings = (TextView) rootView.findViewById(R.id.tv_team_earnings);
            this.tv_apply_to_join = (TextView) rootView.findViewById(R.id.tv_apply_to_join);
            this.rv_my_group = (RecyclerView) rootView.findViewById(R.id.rv_my_group);
            this.rv_team_rank = (RecyclerView) rootView.findViewById(R.id.rv_team_rank);
        }

    }
}