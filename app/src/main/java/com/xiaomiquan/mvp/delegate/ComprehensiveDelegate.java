package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UserSet;

public class ComprehensiveDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comprehensive;
    }

    private void init() {
        viewHolder.tv_drop_ranking.setText(CommonUtils.getString(R.string.ic_Fall) + " " + CommonUtils.getString(R.string.str_drop_ranking));
        viewHolder.tv_rise_ranking.setText(CommonUtils.getString(R.string.ic_Climb) + " " + CommonUtils.getString(R.string.str_rise_ranking));
        viewHolder.tv_drop_ranking.setOnClickListener(onClickListener);
        viewHolder.tv_rise_ranking.setOnClickListener(onClickListener);
        viewHolder.tv_title_volume.setText("BTC 24H" + CommonUtils.getString(R.string.str_volume));
        viewHolder.lin_root.setVisibility(View.GONE);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_drop_ranking:
                    setOnRankingChange(true);
                    break;
                case R.id.tv_rise_ranking:
                    setOnRankingChange(false);
                    break;
            }
        }
    };

    private void setOnRankingChange(boolean isDrop) {
        viewHolder.tv_rise_ranking.setTextColor(isDrop ? CommonUtils.getColor(R.color.color_font3) : CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
        viewHolder.tv_drop_ranking.setTextColor(!isDrop ? CommonUtils.getColor(R.color.color_font3) : CommonUtils.getColor(UserSet.getinstance().getDropColor()));
    }

    public static class ViewHolder {
        public View rootView;
        public LinearLayout lin_coin_index;
        public RecyclerView rv_coin_index;
        public TextView tv_title_volume;
        public RecyclerView rv_deal_num;
        public IconFontTextview tv_rise_ranking;
        public IconFontTextview tv_drop_ranking;
        public LinearLayout lin_ranking;
        public RecyclerView rv_ranking;
        public LinearLayout lin_look_all;
        public LinearLayout lin_root;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lin_coin_index = (LinearLayout) rootView.findViewById(R.id.lin_coin_index);
            this.rv_coin_index = (RecyclerView) rootView.findViewById(R.id.rv_coin_index);
            this.tv_title_volume = (TextView) rootView.findViewById(R.id.tv_title_volume);
            this.rv_deal_num = (RecyclerView) rootView.findViewById(R.id.rv_deal_num);
            this.tv_rise_ranking = (IconFontTextview) rootView.findViewById(R.id.tv_rise_ranking);
            this.tv_drop_ranking = (IconFontTextview) rootView.findViewById(R.id.tv_drop_ranking);
            this.lin_ranking = (LinearLayout) rootView.findViewById(R.id.lin_ranking);
            this.rv_ranking = (RecyclerView) rootView.findViewById(R.id.rv_ranking);
            this.lin_look_all = (LinearLayout) rootView.findViewById(R.id.lin_look_all);
            this.lin_root = (LinearLayout) rootView.findViewById(R.id.lin_root);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}