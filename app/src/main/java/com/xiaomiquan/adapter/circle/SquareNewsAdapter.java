package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class SquareNewsAdapter extends CommonAdapter<UserCircle> {


    private TextView tv_time;
    private TextView tv_con;
    private TextView cv_type1;
    private TextView tv_type2;
    private IconFontTextview icv_shared;

    public SquareNewsAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_square_news, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle s, final int position) {
        tv_time = holder.getView(R.id.tv_time);
        tv_con = holder.getView(R.id.tv_con);
        cv_type1 = holder.getView(R.id.cv_type1);
        tv_type2 = holder.getView(R.id.tv_type2);
        icv_shared = holder.getView(R.id.icv_shared);

    }

}