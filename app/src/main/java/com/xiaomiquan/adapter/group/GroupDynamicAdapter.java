package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class GroupDynamicAdapter extends CommonAdapter<String> {


    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_total_earnings;
    private TextView tv_type;
    private TextView tv_coin_type;
    private TextView tv_commit;
    private TextView tv_price;
    private TextView tv_time;
    private TextView tv_user_name;
    private TextView tv_from;
    private TextView tv_to;

    public GroupDynamicAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_group_dynamic, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_total_earnings = holder.getView(R.id.tv_total_earnings);
        tv_type = holder.getView(R.id.tv_type);
        tv_coin_type = holder.getView(R.id.tv_coin_type);
        tv_commit = holder.getView(R.id.tv_commit);
        tv_price = holder.getView(R.id.tv_price);
        tv_time = holder.getView(R.id.tv_time);
        tv_user_name = holder.getView(R.id.tv_user_name);
        tv_from = holder.getView(R.id.tv_from);
        tv_to = holder.getView(R.id.tv_to);

    }

}