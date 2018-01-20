package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleCreatAdapter extends CommonAdapter<UserCircle> {

    private FontTextview circle_creat_name;
    private FontTextview circle_creat_title;
    private LinearLayout circle_creat_view;

    public CircleCreatAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_creat, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle userCircle, int position) {
        circle_creat_name=holder.getView(R.id.circle_creat_name);
        circle_creat_title=holder.getView(R.id.circle_creat_title);

        circle_creat_name.setText(userCircle.getName());
        circle_creat_title.setText(userCircle.getBrief());
    }
}