package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleDynamicAdapter extends CommonAdapter<UserCircle> {

    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_con;
    private TextView tv_from;
    private TextView tv_praise;
    private TextView tv_praise_num;
    private TextView tv_comment;
    private TextView tv_comment_num;

    public CircleDynamicAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_dynamic, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle s, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_con = holder.getView(R.id.tv_con);
        tv_from = holder.getView(R.id.tv_from);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);


    }

}