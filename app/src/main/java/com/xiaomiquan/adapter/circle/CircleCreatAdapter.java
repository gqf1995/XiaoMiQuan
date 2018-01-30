package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleCreatAdapter extends CommonAdapter<UserCircle> {


    private TextView tv_name;
    private TextView tv_title;
    private LinearLayout lin_root;

    public CircleCreatAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_creat, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle userCircle, int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_title = holder.getView(R.id.tv_title);

        tv_name.setText(userCircle.getName());
        tv_title.setText(userCircle.getBrief());
    }
}