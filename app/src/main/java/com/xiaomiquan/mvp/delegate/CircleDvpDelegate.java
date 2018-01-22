package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaomiquan.R;
import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;

public class CircleDvpDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circledvp;
    }

    private void init() {
//        viewHolder.tv_drop_ranking.setText(CommonUtils.getString(R.string.ic_Fall) + " " + CommonUtils.getString(R.string.str_drop_ranking));
//        viewHolder.tv_rise_ranking.setText(CommonUtils.getString(R.string.ic_Climb) + " " + CommonUtils.getString(R.string.str_rise_ranking));
//        viewHolder.tv_drop_ranking.setOnClickListener(onClickListener);
//        viewHolder.tv_rise_ranking.setOnClickListener(onClickListener);
//        viewHolder.tv_title_volume.setText("BTC 24H" + CommonUtils.getString(R.string.str_volume));
//        viewHolder.lin_root.setVisibility(View.GONE);
    }

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
////            switch (view.getId()) {
////                case R.id.tv_drop_ranking:
////                    setOnRankingChange(true);
////                    break;
////                case R.id.tv_rise_ranking:
////                    setOnRankingChange(false);
////                    break;
////            }
//        }
//    };

    private void setOnRankingChange(boolean isDrop) {
//        viewHolder.tv_rise_ranking.setTextColor(isDrop ? CommonUtils.getColor(R.color.color_font3) : CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
//        viewHolder.tv_drop_ranking.setTextColor(!isDrop ? CommonUtils.getColor(R.color.color_font3) : CommonUtils.getColor(UserSet.getinstance().getDropColor()));
    }

    public static class ViewHolder {
        public View rootView;
        public LinearLayout lin_root;
        public RecyclerView circledvp_card;
        public RecyclerView circle_dvp_hot;
        public LinearLayout lin_ranking;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lin_root=(LinearLayout) rootView.findViewById(R.id.lin_root);
            this.lin_ranking=(LinearLayout) rootView.findViewById(R.id.lin_ranking);
            this.circledvp_card=(RecyclerView) rootView.findViewById(R.id.circledvp_card);
            this.circle_dvp_hot=(RecyclerView) rootView.findViewById(R.id.circle_dvp_hot);
            this.swipeRefreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}