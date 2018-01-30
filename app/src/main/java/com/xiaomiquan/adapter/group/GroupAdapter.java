package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupAdapter extends CommonAdapter<ExchangeData> {


    private TextView tv_title;
    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_num;
    private TextView tv_add_percent;
    private TextView tv_today_percent;
    private TextView tv_deal;
    private TextView tv_look;

    DefaultClickLinsener defaultClickLinsener;

    public GroupAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_group, datas);
    }
    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeData exchangeData, final int position) {
        tv_title = holder.getView(R.id.tv_title);
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_num = holder.getView(R.id.tv_num);
        tv_add_percent = holder.getView(R.id.tv_add_percent);
        tv_today_percent = holder.getView(R.id.tv_today_percent);
        tv_deal = holder.getView(R.id.tv_deal);
        tv_look = holder.getView(R.id.tv_look);

        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
        tv_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
    }
}
