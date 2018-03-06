package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RevenueRankingAdapter extends CommonAdapter<String> {


    private TextView tv_num;
    private TextView tv_last_week_earnings;
    private TextView tv_rate_title;
    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_focuse_num;
    private TextView tv_details;

    public RevenueRankingAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_revenue_ranking, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        tv_num = holder.getView(R.id.tv_num);
        tv_last_week_earnings = holder.getView(R.id.tv_last_week_earnings);
        tv_rate_title = holder.getView(R.id.tv_rate_title);
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_focuse_num = holder.getView(R.id.tv_focuse_num);
        tv_details = holder.getView(R.id.tv_details);

    }

}
