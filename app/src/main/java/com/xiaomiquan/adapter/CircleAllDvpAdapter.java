package com.xiaomiquan.adapter;

import android.content.Context;

import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleAllDvpAdapter extends CommonAdapter<UserCircle> {
    CircleImageView dvp_head;
    FontTextview dvp_name;


    public CircleAllDvpAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_dvp, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle userCircle, int position) {
//        dvp_head=holder.getView(R.id.dvp_head);
//        dvp_name=holder.getView(R.id.dvp);

    }
}