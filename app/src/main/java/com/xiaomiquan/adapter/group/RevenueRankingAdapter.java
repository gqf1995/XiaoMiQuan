package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.GroupRank;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RevenueRankingAdapter extends CommonAdapter<GroupItem> {


    private TextView tv_num;
    private TextView tv_last_week_earnings;
    private TextView tv_rate_title;
    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_focuse_num;
    private TextView tv_details;
    String type;

    public RevenueRankingAdapter(Context context , List<GroupItem> datas,String type) {
        super(context, R.layout.adapter_revenue_ranking, datas);
        this.type=type;
    }


    public void setDatas(List<GroupItem> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, GroupItem s, int position) {
        tv_num = holder.getView(R.id.tv_num);
        tv_last_week_earnings = holder.getView(R.id.tv_last_week_earnings);
        tv_rate_title = holder.getView(R.id.tv_rate_title);
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_focuse_num = holder.getView(R.id.tv_focuse_num);
        tv_details = holder.getView(R.id.tv_details);

        switch (Integer.parseInt(type)) {
            case 0:
                tv_rate_title.setText("今日收益");
                break;
            case 1:
                tv_rate_title.setText("上周收益");
                break;
            case 2:
                tv_rate_title.setText("上月收益");
                break;
            case 3:
                tv_rate_title.setText("累计收益");
                break;
        }

        tv_num.setText(position + 1 + "");
        tv_last_week_earnings.setText("+" + s.getRate() + "%");
        tv_name.setText(s.getName());
        tv_focuse_num.setText(s.getAttentionCount());
    }

}
