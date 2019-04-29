package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleFindAdapter extends CommonAdapter<UserCircle> {


    private TextView tv_name;
    private TextView tv_creator;
    private TextView tv_num;

    public CircleFindAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_find, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_creator = holder.getView(R.id.tv_creator);
        tv_num = holder.getView(R.id.tv_num);

    }

}