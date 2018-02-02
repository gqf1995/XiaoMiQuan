package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleAllDvpAdapter extends CommonAdapter<UserCircle> {

    private ImageView cv_head;
    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_username;
    private TextView tv_con;
    private TextView tv_num;

    public CircleAllDvpAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_dvp, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle userCircle, int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_type = holder.getView(R.id.tv_type);
        tv_username = holder.getView(R.id.tv_username);
        tv_con = holder.getView(R.id.tv_con);
        tv_num = holder.getView(R.id.tv_num);

        tv_num.setText(userCircle.getMemberCount());
        tv_name.setText(userCircle.getName());
        tv_con.setText(userCircle.getBrief());
        tv_num.setText(userCircle.getMemberCount());


        GlideUtils.loadImage(userCircle.getAvatar(), cv_head);


    }
}