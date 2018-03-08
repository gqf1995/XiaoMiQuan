package com.xiaomiquan.mvp.delegate;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.chat.ChatLiveListActivity;
import com.xiaomiquan.mvp.activity.group.MyAccountActivity;
import com.xiaomiquan.mvp.activity.group.SimulatedTradingActivity;

public class HomeDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.rootView.getContext(), ChatLiveListActivity.class);
                ((Activity) viewHolder.rootView.getContext()).startActivity(intent);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    public static class ViewHolder {
        public View rootView;
        public Button chart;
        public Button btn_my_position;
        public Button btn_his_position;
        public FrameLayout fl_web;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.chart = (Button) rootView.findViewById(R.id.chart);
            this.btn_my_position = (Button) rootView.findViewById(R.id.btn_my_position);
            this.btn_his_position = (Button) rootView.findViewById(R.id.btn_his_position);
            this.fl_web = (FrameLayout) rootView.findViewById(R.id.fl_web);
        }

    }
}